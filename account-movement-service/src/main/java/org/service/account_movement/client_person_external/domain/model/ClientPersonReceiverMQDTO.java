package org.service.account_movement.client_person_external.domain.model;

import java.util.UUID;

public record ClientPersonReceiverMQDTO(
    UUID clientID,
    Boolean clientState,
    PersonDTO person
    ) {
}
