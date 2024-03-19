package org.example.repository;

import java.util.Collection;
import java.util.List;

public interface OrganizerRepository<T,Id> {
    void addOrganizer(T elem);

    void deleteOrganizer(T elem);

    void updateOrganizer(T elem, Id id);

    T findByIdOrganizer(Id id);

    List<T> findAllOrganizer();

}
