package org.service.client_person.client.domain.model;

import java.util.UUID;

public record ClientPersonProducerMQDTO(
    UUID clientID,
    Boolean clientState,
    PersonMQDTO person
) {
}
