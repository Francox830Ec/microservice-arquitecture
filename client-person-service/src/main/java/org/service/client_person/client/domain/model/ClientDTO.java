package org.service.client_person.client.domain.model;

import org.service.client_person.person.domain.model.PersonDTO;

import java.util.UUID;

public record ClientDTO(
        UUID id,
        String password,
        Boolean state,
        PersonDTO person
) {
}
