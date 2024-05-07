package org.example.validation;

import org.example.Organizer;

import java.util.Objects;

public class OrganizerValidator implements Validator<Organizer> {
    @Override
    public void validate(Organizer entity) throws RuntimeException {
        if (entity.getId() < 1) {
            throw new RuntimeException("ID can't be < 1");
        }
        if (Objects.equals(entity.getPassword(), "")) {
            throw new RuntimeException("Password can't be null");
        }
        if (Objects.equals(entity.getUsername(), ""))
            throw new RuntimeException("Username can't be null");
    }
}
