package org.service.client_person.client.application.usecases.contract;

import org.service.client_person.client.domain.model.ClientDTO;

public interface ICreateClientUseCase {
    ClientDTO create(ClientDTO clientDTO);
}
