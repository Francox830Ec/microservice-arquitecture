package org.service.account_movement.movement.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MovementDTO(
        UUID id,
        @NotNull
        UUID accountId,
        LocalDateTime date,
        String type,
        BigDecimal initialBalance,
        @NotNull
        BigDecimal value,
        BigDecimal finalBalance
) {
}
