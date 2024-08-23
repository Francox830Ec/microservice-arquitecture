package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.IFindAllClientUseCase;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.repository.IClientRepository;

import java.util.List;

public class FindAllClientUseCaseImpl implements IFindAllClientUseCase {
    private final IClientRepository repository;

    public FindAllClientUseCaseImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ClientDTO> findAll() {
        return repository.findAll();
    }
}
