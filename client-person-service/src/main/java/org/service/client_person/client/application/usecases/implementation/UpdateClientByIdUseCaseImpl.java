package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.IUpdateClientByIdUseCase;
import org.service.client_person.client.application.usecases.exception.RecursoNotFoundException;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.repository.IClientRepository;
import org.service.client_person.person.domain.model.PersonDTO;

import java.util.UUID;

public class UpdateClientByIdUseCaseImpl implements IUpdateClientByIdUseCase {
    private final IClientRepository repository;

    public UpdateClientByIdUseCaseImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientDTO updateById(UUID id, ClientDTO requestClientDTO) {
        return repository.findById(id)
                .map(savedClientDTO -> repository.update(
                        new ClientDTO(savedClientDTO.id(), requestClientDTO.password(), requestClientDTO.state(),
                                new PersonDTO(
                                    savedClientDTO.person().id(),
                                    requestClientDTO.person().name(),
                                    requestClientDTO.person().gender(),
                                    requestClientDTO.person().age(),
                                    savedClientDTO.person().identification(),
                                    requestClientDTO.person().address(),
                                    requestClientDTO.person().phone()))))
                .orElseThrow(() -> new RecursoNotFoundException(new StringBuilder("Client not found with id: ").append(id).toString()));
    }
}
