package org.service.account_movement.account.application.usecases.exception;

public class RecursoNotFoundException extends RuntimeException{
    public RecursoNotFoundException() {
        super();
    }

    public RecursoNotFoundException(String message) {
        super(message);
    }
}