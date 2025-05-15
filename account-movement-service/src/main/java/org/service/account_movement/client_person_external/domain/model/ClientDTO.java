package org.service.account_movement.client_person_external.domain.model;

import org.service.account_movement.account.domain.model.AccountDTO;

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
