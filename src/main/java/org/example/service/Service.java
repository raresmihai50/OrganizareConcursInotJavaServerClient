package org.example.service;

import org.example.domain.Organizer;
import org.example.domain.Participant;
import org.example.domain.Trial;
import org.example.repository.OrganizerDBRepository;
import org.example.repository.ParticipantDBRepository;
import org.example.repository.TrialDBRepository;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class Service {
    OrganizerDBRepository orgRepo;
    TrialDBRepository trRepo;
    ParticipantDBRepository partRepo;
    public Service(OrganizerDBRepository orgRepo, TrialDBRepository trRepo, ParticipantDBRepository partRepo){
        this.orgRepo = orgRepo;
        this.trRepo = trRepo;
        this.partRepo = partRepo;
    }
    public Organizer findOrganizerById(int id){
        return orgRepo.findByIdOrganizer(id);
    }
    public boolean verifica_parola(String parola_string, String parola_string_2) throws NoSuchAlgorithmException {
        return Objects.equals(parola_string, parola_string_2);
    }
    public Organizer findOrganizerByUsername(String username){
        return orgRepo.findByUsernameOrganizer(username);
    }
    public List<Participant> findAllParticipant(){
        return partRepo.findAllParticipant();
    }
    public Participant findParticipantById(int id){
        return partRepo.findByIdParticipant(id);
    }
    public void AddParticipant(Participant participant){
        partRepo.addParticipant(participant);
    }
    public Trial findTrialByTypeDetails(String type, String details){
        return trRepo.findByTypeDetailsTrial(type, details);
    }
    public void addParticipant(Participant participant){
        partRepo.addParticipant(participant);
    }
}
