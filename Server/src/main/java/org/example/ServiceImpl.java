package org.example;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImpl implements IService {

    //OrganizerDBRepository orgRepo;
    ParticipantDBRepository partRepo;
    TrialDBRepository trRepo;
    OrganizerORMRepository orgRepo;
    private Map<String, IObserver> loggedOrganizers;

    /*public ServiceImpl(OrganizerDBRepository orgRepo, ParticipantDBRepository partRepo, TrialDBRepository trRepo) {
        this.orgRepo = orgRepo;
        this.partRepo = partRepo;
        this.trRepo = trRepo;
        this.loggedOrganizers = new ConcurrentHashMap<>();
    }*/
    public ServiceImpl(OrganizerORMRepository orgRepo, ParticipantDBRepository partRepo, TrialDBRepository trRepo) {
        this.orgRepo = orgRepo;
        this.partRepo = partRepo;
        this.trRepo = trRepo;
        this.loggedOrganizers = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Organizer organizer, IObserver observer) throws MessageError {
        //Organizer org = orgRepo.findByIdOrganizer(organizer.getId());
        Organizer org = orgRepo.findByUsernameAndPasswordOrganizer(organizer.getUsername(),organizer.getPassword());
        if (org != null) {
            if (loggedOrganizers.get(organizer.getUsername()) != null)
                throw new MessageError("Organizer already logged in.");
            loggedOrganizers.put(organizer.getUsername(), observer);
        } else
            throw new MessageError("Authentication failed.");
    }

    @Override
    public synchronized void logout(Organizer organizer, IObserver observer) throws MessageError {
        IObserver localClient = loggedOrganizers.remove(organizer.getUsername());
        if (localClient == null)
            throw new MessageError("Organizer " + organizer.getUsername() + " is not logged in.");
    }

    @Override
    public synchronized List<Participant> findParticipantsByTrials(List<Trial> trials) throws MessageError {
        //logger.info("Entering findParticipantsByTrials with trials {}", trials);
        List<Participant> participants = findAllParticipant();
        List<Participant> result = new ArrayList<Participant>();

        for (Participant part : participants) {
            List<Trial> partTrials = part.getTrials();
            boolean allTrialsFound = true;

            // Verifica fiecare trial din lista data
            for (Trial trial : trials) {
                // Verifică dacă participantul are trialul din lista dată
                if (!partTrials.contains(trial)) {
                    allTrialsFound = false;
                    break; // Nu mai este nevoie să continuăm căutarea dacă lipsește un trial
                }
            }

            // Daca toate trialele din lista data sunt gasite în lista de triale a participantului, adauga-l la rezultat
            if (allTrialsFound) {
                result.add(part);
            }
        }
        System.out.println(result);
        return result;
    }

    @Override
    public synchronized List<Participant> findAllParticipant() throws MessageError {
        return partRepo.findAllParticipant();
    }

    @Override
    public synchronized void addParticipant(Participant participant) throws MessageError {
        partRepo.addParticipant(participant);
        notifyOrganizersParticipantAdded(participant);
    }

    @Override
    public synchronized Trial findTrialByTypeDetails(String type, String details) throws MessageError {
        return trRepo.findByTypeDetailsTrial(type, details);
    }

    private final int defaultThreadsNo = 5;

    private void notifyOrganizersParticipantAdded(Participant participant) throws MessageError {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for (Organizer org : orgRepo.findAllOrganizer()) {
            IObserver client = loggedOrganizers.get(org.getUsername());
            if (client != null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + org.getId() + "] participantAdded [" + participant.getId().toString() + "] was added.");
                        client.participantAdded(participant);
                    } catch (MessageError e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }

        executor.shutdown();
    }
}
