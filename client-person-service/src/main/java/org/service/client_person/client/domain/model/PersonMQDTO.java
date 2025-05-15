package org.service.client_person.client.domain.model;

import java.util.UUID;

public record PersonMQDTO(
        UUID personID,
        String personName,
        String personIdentification
) {
}
