package org.service.client_person.person.domain.port.out;

import org.service.client_person.person.domain.model.PersonDTO;

public interface IPersonRepository {
    PersonDTO create(PersonDTO personDTO);
}
