package org.service.client_person.person.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record PersonDTO(
    UUID id,
    @NotNull @NotEmpty
    String name,
    @NotNull @NotEmpty
    String gender,
    @NotNull @Positive
    Integer age,
    @NotNull @NotEmpty
    String identification,
    @NotNull @NotEmpty
    String address,
    @NotNull @NotEmpty
    String phone
) {
}
