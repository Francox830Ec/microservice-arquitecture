package org.service.client_person.client.application.usecases.contract;

import java.util.UUID;

public interface IDeleteClientByIdUseCase {
    void deleteById(UUID id);
}
