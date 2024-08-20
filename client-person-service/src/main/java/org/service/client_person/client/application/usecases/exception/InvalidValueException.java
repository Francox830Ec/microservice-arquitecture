package org.service.client_person.client.application.usecases.exception;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException() {
        super();
    }

    public InvalidValueException(final String message) {
        super(message);
    }
}
