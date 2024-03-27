package org.example.service;

import org.example.domain.Organizer;
import org.example.repository.OrganizerDBRepository;
import org.example.repository.ParticipantDBRepository;
import org.example.repository.TrialDBRepository;

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
}
