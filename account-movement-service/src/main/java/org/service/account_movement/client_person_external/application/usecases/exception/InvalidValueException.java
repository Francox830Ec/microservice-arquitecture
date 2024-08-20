package org.service.account_movement.client_person_external.application.usecases.exception;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException() {
        super();
    }

    public InvalidValueException(final String message) {
        super(message);
    }
}
