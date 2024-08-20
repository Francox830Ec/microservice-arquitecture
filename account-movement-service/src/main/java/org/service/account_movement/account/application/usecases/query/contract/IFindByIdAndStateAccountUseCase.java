package org.service.account_movement.account.application.usecases.query.contract;

import org.service.account_movement.account.domain.model.AccountDTO;

import java.util.Optional;
import java.util.UUID;

public interface IFindByIdAndStateAccountUseCase {
    Optional<AccountDTO> findByIdAndState(UUID id, String state);
}
