package org.service.account_movement.account.application.usecases.exception;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException() {
        super();
    }

    public InvalidValueException(final String message) {
        super(message);
    }
}
