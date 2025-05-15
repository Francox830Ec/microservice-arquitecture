package org.service.client_person.person.application.usecases.command.implementation;

import org.service.client_person.person.application.usecases.command.contract.ICreatePersonUseCase;
import org.service.client_person.person.domain.model.PersonDTO;
import org.service.client_person.person.domain.port.out.repository.IPersonRepository;

public class CreatePersonUseCaseImpl implements ICreatePersonUseCase {
    private final IPersonRepository commandRepository;

    public CreatePersonUseCaseImpl(IPersonRepository commandRepository) {
        this.commandRepository = commandRepository;
    }


    @Override
    public PersonDTO create(PersonDTO personDTO) {
        return commandRepository.create(personDTO);
    }
}
