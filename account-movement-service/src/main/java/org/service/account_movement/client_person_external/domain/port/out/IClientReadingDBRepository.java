package org.service.account_movement.client_person_external.domain.port.out;

import org.service.account_movement.account.domain.model.ClientDTO;

import java.util.Optional;
import java.util.UUID;

public interface IClientReadingDBRepository {
    void save (ClientDTO dto);
    Optional<ClientDTO> findById(UUID clientID);
    Optional<ClientDTO> findByPersonIdentification(String personIdentification);
}
