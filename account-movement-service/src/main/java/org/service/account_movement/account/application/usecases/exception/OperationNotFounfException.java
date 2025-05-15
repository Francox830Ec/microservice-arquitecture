package org.service.account_movement.account.application.usecases.exception;

public class OperationNotFounfException extends RuntimeException {
    public OperationNotFounfException() {
        super();
    }

    public OperationNotFounfException(final String message) {
        super(message);
    }
}
