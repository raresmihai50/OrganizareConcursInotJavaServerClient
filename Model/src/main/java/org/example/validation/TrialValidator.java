package org.example.validation;

import org.example.Trial;

import java.util.Objects;

public class TrialValidator implements Validator<Trial> {

    @Override
    public void validate(Trial entity) throws RuntimeException {
        if (Objects.equals(entity.getdetails(), "")) {
            throw new RuntimeException("Details can't be null");
        }
        if (entity.getId() < 1) {
            throw new RuntimeException("ID can't be < 1");
        }
        if (Objects.equals(entity.gettype(), "")) {
            throw new RuntimeException("Type can't be null");
        }
    }
}
