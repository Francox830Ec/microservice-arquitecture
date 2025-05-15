package org.service.client_person.client.application.usecases.contract;

import org.service.client_person.client.domain.model.ClientDTO;

import java.util.Optional;
import java.util.UUID;

public interface IFindByIdClientUseCase {
    Optional<ClientDTO> findById(UUID id);
}
