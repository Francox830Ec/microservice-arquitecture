package org.service.client_person.person.application.usecases.command.contract;

import org.service.client_person.person.domain.model.PersonDTO;

public interface ICreatePersonUseCase {
    PersonDTO create(PersonDTO personDTO);
}
