package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Organizer;
import org.example.domain.Participant;
import org.example.domain.Trial;
import org.example.repository.OrganizerDBRepository;
import org.example.repository.ParticipantDBRepository;
import org.example.repository.TrialDBRepository;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service implements IService {
    OrganizerDBRepository orgRepo;
    TrialDBRepository trRepo;
    ParticipantDBRepository partRepo;
    private static final Logger logger = LogManager.getLogger();

    public Service(OrganizerDBRepository orgRepo, TrialDBRepository trRepo, ParticipantDBRepository partRepo) {
        this.orgRepo = orgRepo;
        this.trRepo = trRepo;
        this.partRepo = partRepo;

    }

    public Organizer findOrganizerById(int id) {
        return orgRepo.findByIdOrganizer(id);
    }

    public boolean verifica_parola(String parola_string, String parola_string_2) throws NoSuchAlgorithmException {
        return Objects.equals(parola_string, parola_string_2);
    }

    public Organizer findOrganizerByUsername(String username) {
        return orgRepo.findByUsernameOrganizer(username);
    }

    public List<Participant> findAllParticipant() {
        return partRepo.findAllParticipant();
    }

    public Participant findParticipantById(int id) {
        return partRepo.findByIdParticipant(id);
    }

    public void AddParticipant(Participant participant) {
        partRepo.addParticipant(participant);
    }

    public Trial findTrialByTypeDetails(String type, String details) {
        return trRepo.findByTypeDetailsTrial(type, details);
    }

    public void addParticipant(Participant participant) {
        partRepo.addParticipant(participant);
    }

    public List<Participant> findParticipantsByTrials(List<Trial> trials) {
        logger.info("Entering findParticipantsByTrials with trials {}", trials);
        List<Participant> participants = findAllParticipant();
        List<Participant> result = new ArrayList<Participant>();

        for (Participant part : participants) {
            List<Trial> partTrials = part.getTrials();
            boolean allTrialsFound = true;

            // Verifica fiecare trial din lista dată
            for (Trial trial : trials) {
                // Verifică dacă participantul are trialul din lista dată
                if (!partTrials.contains(trial)) {
                    allTrialsFound = false;
                    break; // Nu mai este nevoie să continuăm căutarea dacă lipsește un trial
                }
            }

            // Dacă toate trialele din lista dată sunt găsite în lista de triale a participantului, adaugă-l la rezultat
            if (allTrialsFound) {
                result.add(part);
            }
        }
        System.out.println(result);
        return result;
    }

}

