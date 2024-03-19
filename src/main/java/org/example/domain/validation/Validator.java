package org.example.domain.validation;

public interface Validator<Elem> {
    void validate(Elem entity) throws RuntimeException;
}
