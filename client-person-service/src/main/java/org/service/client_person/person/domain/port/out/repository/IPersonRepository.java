package org.service.client_person.person.domain.port.out.repository;

import org.service.client_person.person.domain.model.PersonDTO;

import java.util.Optional;
import java.util.UUID;

public interface IPersonRepository {
    PersonDTO create(PersonDTO personDTO);
    Optional<PersonDTO> findById(UUID id);
    Optional<PersonDTO> findByIdentification(String identification);
}
