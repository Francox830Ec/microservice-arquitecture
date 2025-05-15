package org.service.account_movement.account.domain.model;

import org.service.account_movement.movement.domain.model.MovementDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AccountDTOReadingDB(
        UUID id,
        UUID clientID,
        String number,
        String type,
        BigDecimal initialBalance,
        String state,
        List<MovementDTO> movements
) {
}
