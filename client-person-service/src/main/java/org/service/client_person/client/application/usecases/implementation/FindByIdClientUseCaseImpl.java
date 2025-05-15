package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.IFindByIdClientUseCase;
import org.service.client_person.client.application.usecases.exception.RecursoNotFoundException;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.repository.IClientRepository;

import java.util.Optional;
import java.util.UUID;

public class FindByIdClientUseCaseImpl implements IFindByIdClientUseCase {
    private final IClientRepository repository;

    public FindByIdClientUseCaseImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ClientDTO> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id).
                orElseThrow(() -> new RecursoNotFoundException("Client with id " + id + " not found")));
    }
}
