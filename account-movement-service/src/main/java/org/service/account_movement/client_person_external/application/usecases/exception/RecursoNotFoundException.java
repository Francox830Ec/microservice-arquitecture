package org.service.account_movement.client_person_external.application.usecases.exception;

public class RecursoNotFoundException extends RuntimeException{
    public RecursoNotFoundException() {
        super();
    }

    public RecursoNotFoundException(String message) {
        super(message);
    }
}