package org.service.client_person.client.application.usecases.exception;

public class RecursoNotFoundException extends RuntimeException{
    public RecursoNotFoundException() {
        super();
    }

    public RecursoNotFoundException(String message) {
        super(message);
    }
}