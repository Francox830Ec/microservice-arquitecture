package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.IDeleteClientByIdUseCase;
import org.service.client_person.client.application.usecases.exception.RecursoNotFoundException;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.repository.IClientRepository;

import java.util.UUID;

public class DeleteClientByIdUseCaseImpl implements IDeleteClientByIdUseCase {
    private final IClientRepository repository;

    public DeleteClientByIdUseCaseImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteById(UUID id) {
        if(!repository.existsById(id))
            throw new RecursoNotFoundException(new StringBuilder("Client not found with id: ").append(id).toString());

        repository.deleteById(id);
    }

    @Override
    public ClientDTO deleteLogicalById(UUID id) {
        return repository.findById(id)
                .map(savedClientDTO -> repository.update(
                        new ClientDTO(savedClientDTO.id(), savedClientDTO.password(), false, savedClientDTO.person())))
                .orElseThrow(() -> new RecursoNotFoundException(new StringBuilder("Client not found with id: ").append(id).toString()));
    }
}
