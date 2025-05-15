package org.service.client_person.client.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.service.client_person.person.domain.model.PersonDTO;

import java.util.UUID;

public record ClientDTO(
        UUID id,
        @NotNull @NotEmpty
        String password,
        Boolean state,

        @NotNull
        PersonDTO person
) {
}
