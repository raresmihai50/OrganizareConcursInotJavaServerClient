package org.example;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface IService {
    void login(Organizer organizer, IObserver observer) throws MessageError;

    void logout(Organizer organizer, IObserver observer) throws MessageError;

    List<Participant> findParticipantsByTrials(List<Trial> trials) throws MessageError;

    public List<Participant> findAllParticipant() throws MessageError;

    public void addParticipant(Participant participant) throws MessageError;

    public Trial findTrialByTypeDetails(String type, String details) throws MessageError;
}
