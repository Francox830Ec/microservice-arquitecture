package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.ICreateClientUseCase;
import org.service.client_person.client.application.usecases.exception.InvalidValueException;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.repository.IClientRepository;
import org.service.client_person.person.domain.model.PersonDTO;
import org.service.client_person.person.domain.port.out.repository.IPersonRepository;

public class CreateClientUseCaseImpl implements ICreateClientUseCase {
    private final IClientRepository repository;
    private final IPersonRepository personRepository;

    public CreateClientUseCaseImpl(IClientRepository repository,
                                   IPersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @Override
    public ClientDTO create(ClientDTO clientDTO) {
        validateCreation(clientDTO);
        validateIdentification(clientDTO.person().identification());
        return repository.create(buildClientDTO(clientDTO));
    }

    private void validateCreation(ClientDTO requestClientDTO) {
        if(requestClientDTO.id() != null || requestClientDTO.person().id() != null){
            throw new InvalidValueException("This service only allows you to create new clients/persons.");
        }

    }

    private static ClientDTO buildClientDTO(ClientDTO requestClientDTO) {
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

    private void validateIdentification(String identification) {
        personRepository.findByIdentification(identification)
                .ifPresent(personDTO -> {
                    throw new InvalidValueException("Identification " + identification + " already exists.");
                });
    }
}
