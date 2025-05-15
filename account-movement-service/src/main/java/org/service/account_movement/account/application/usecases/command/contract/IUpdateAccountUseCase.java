package org.service.account_movement.account.application.usecases.command.contract;

import org.service.account_movement.account.domain.model.AccountDTO;

import java.util.UUID;

public interface IUpdateAccountUseCase {
    AccountDTO updateById (UUID id, AccountDTO accountDTO);
}
