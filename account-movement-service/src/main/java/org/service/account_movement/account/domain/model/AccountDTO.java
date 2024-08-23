package org.service.account_movement.account.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.service.account_movement.movement.domain.model.MovementDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AccountDTO(
        UUID id,
        @NotNull
        UUID clientID,
        String number,
        @NotNull @NotEmpty
        String type,
        @NotNull
        BigDecimal initialBalance,
        Boolean state,
        List<MovementDTO> movements
) {
}