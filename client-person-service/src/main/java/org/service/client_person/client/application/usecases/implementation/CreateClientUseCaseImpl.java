package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.ICreateClientUseCase;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.IClientRepository;

public class CreateClientUseCaseImpl implements ICreateClientUseCase {
    private final IClientRepository repository;

    public CreateClientUseCaseImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientDTO create(ClientDTO clientDTO) {
        return repository.create(clientDTO);
    }
}
