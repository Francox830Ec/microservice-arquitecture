package org.service.account_movement.client_person_external.application.usecases.query.contract;

import org.service.account_movement.client_person_external.domain.model.ClientDTO;

import java.util.Optional;
import java.util.UUID;

public interface IFindByIdClientUseCase {
    Optional<ClientDTO> findByID(UUID clientID);
}
