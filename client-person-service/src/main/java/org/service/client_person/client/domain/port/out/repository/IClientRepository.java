package org.service.client_person.client.domain.port.out.repository;

import org.service.client_person.client.domain.model.ClientDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientRepository {
    ClientDTO create(ClientDTO clientDTO);
    ClientDTO update(ClientDTO clientDTO);
    void deleteById(UUID id);
    List<ClientDTO> findAll();
    Optional<ClientDTO> findById(UUID id);
    boolean existsById(UUID id);
}

