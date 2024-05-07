package org.example.validation;

import org.example.Participant;

import java.util.Objects;

public class ParticipantValidator implements Validator<Participant> {
    @Override
    public void validate(Participant entity) throws RuntimeException {
        if (entity.getAge() < 4) {
            throw new RuntimeException("Age can't be < 4");
        }
        if (entity.getId() == 0) {
            throw new RuntimeException("ID can't be = 0");
        }
        if (Objects.equals(entity.getName(), "")) {
            throw new RuntimeException("Name can't be null");
        }
        if (entity.getTrials().isEmpty()) {
            throw new RuntimeException("There must be at least 1 trial");
        }
    }
}
