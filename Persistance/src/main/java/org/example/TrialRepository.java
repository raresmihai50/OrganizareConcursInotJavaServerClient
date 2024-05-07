package org.example;

import java.util.List;

public interface TrialRepository<T, Id> {
    void addTrial(T elem);

    void deleteTrial(T elem);

    void updateTrial(T elem, Id id);

    T findByIdTrial(Id id);

    List<T> findAllTrial();

}
