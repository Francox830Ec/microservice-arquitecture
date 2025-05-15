package org.service.account_movement.client_person_external.domain.model;

import java.util.UUID;

public record PersonDTO(
        UUID personID,
        String personName,
        String personIdentification
) {
}