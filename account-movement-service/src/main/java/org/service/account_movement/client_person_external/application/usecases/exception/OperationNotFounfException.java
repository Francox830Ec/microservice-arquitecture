package org.service.account_movement.client_person_external.application.usecases.exception;

public class OperationNotFounfException extends RuntimeException {
    public OperationNotFounfException() {
        super();
    }

    public OperationNotFounfException(final String message) {
        super(message);
    }
}
