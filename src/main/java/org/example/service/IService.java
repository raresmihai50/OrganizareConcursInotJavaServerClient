package org.example.service;

import org.example.domain.Organizer;
import org.example.domain.Participant;
import org.example.domain.Trial;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface IService {
    public Organizer findOrganizerById(int id);
    public boolean verifica_parola(String parola_string, String parola_string_2) throws NoSuchAlgorithmException;
    public Organizer findOrganizerByUsername(String username);
    public Participant findParticipantById(int id);
    public void AddParticipant(Participant participant);
    public Trial findTrialByTypeDetails(String type, String details);
    public void addParticipant(Participant participant);
    public List<Participant> findParticipantsByTrials(List<Trial> trials);
}
