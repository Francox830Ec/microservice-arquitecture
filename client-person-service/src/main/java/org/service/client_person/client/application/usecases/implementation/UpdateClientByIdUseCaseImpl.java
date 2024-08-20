package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.IUpdateClientByIdUseCase;
import org.service.client_person.client.application.usecases.exception.RecursoNotFoundException;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.IClientRepository;

import java.util.UUID;

public class UpdateClientByIdUseCaseImpl implements IUpdateClientByIdUseCase {
    private final IClientRepository repository;

    public UpdateClientByIdUseCaseImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientDTO updateById(UUID id, ClientDTO clientRequestDTO) {
        return repository.findById(id)
                .map(clientSavedDTO -> repository.update(new ClientDTO(clientSavedDTO.id(),
                            clientRequestDTO.password(),
                            clientRequestDTO.state(),
                            clientRequestDTO.person()))
                )
                .orElseThrow(() -> new RecursoNotFoundException(new StringBuilder("Client not found with id: ")
                        .append(id).toString()));
    }
}
