package org.example.repository;

import java.util.Collection;
import java.util.List;

public interface ParticipantRepository<T, Id> {
    void addParticipant(T elem);

    void deleteParticipant(T elem);

    void updateParticipant(T elem, Id id);

    T findByIdParticipant(Id id);

    List<T> findAllParticipant();

}
