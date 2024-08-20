package org.service.account_movement.account.domain.model;

import java.util.List;
import java.util.UUID;

public record ClientDTO(
        UUID clientID,
        Boolean clientState,
        String personName,
        String personIdentification,
        List<AccountDTO> accounts
) {
}
