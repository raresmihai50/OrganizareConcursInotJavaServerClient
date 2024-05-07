package org.example;

public interface IObserver {
    void participantAdded(Participant participant) throws MessageError;
}
