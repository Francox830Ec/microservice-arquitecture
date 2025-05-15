package org.service.account_movement.account.application.usecases.command.contract;

import org.service.account_movement.account.domain.model.AccountDTO;

public interface ICreateAccountUseCase {
    AccountDTO create(AccountDTO accountDTO);
}
