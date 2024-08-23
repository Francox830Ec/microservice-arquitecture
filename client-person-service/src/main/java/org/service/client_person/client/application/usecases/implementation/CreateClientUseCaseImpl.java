package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.ICreateClientUseCase;
import org.service.client_person.client.application.usecases.exception.InvalidValueException;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.IClientRepository;
import org.service.client_person.person.domain.model.PersonDTO;

public class CreateClientUseCaseImpl implements ICreateClientUseCase {
    private final IClientRepository repository;

    public CreateClientUseCaseImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientDTO create(ClientDTO clientDTO) {
        return repository.create(validateCreation(clientDTO));
    }

    private ClientDTO validateCreation(ClientDTO requestClientDTO) {
        if(requestClientDTO.id() != null || requestClientDTO.person().id() != null){
            throw new InvalidValueException("This service only allows you to create new clients/persons.");
        }

        return new ClientDTO(null, requestClientDTO.password(), true,
                new PersonDTO(
                        null,
                        requestClientDTO.person().name(),
                        requestClientDTO.person().gender(),
                        requestClientDTO.person().age(),
                        requestClientDTO.person().identification(),
                        requestClientDTO.person().address(),
                        requestClientDTO.person().phone()));
    }
}
