package org.service.client_person.person.domain.model;

import java.util.UUID;

public record PersonDTO(
    UUID id,
    String name,
    String gender,
    Integer age,
    String identification,
    String address,
    String phone
) {
}
