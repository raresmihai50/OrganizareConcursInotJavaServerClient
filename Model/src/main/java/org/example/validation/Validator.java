package org.example.validation;

public interface Validator<Elem> {
    void validate(Elem entity) throws RuntimeException;
}
