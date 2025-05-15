package org.service.client_person.client.application.usecases.contract;

import org.service.client_person.client.domain.model.ClientDTO;

import java.util.UUID;

public interface IUpdateClientByIdUseCase {
    ClientDTO updateById (UUID id, ClientDTO clientDTO);
}
