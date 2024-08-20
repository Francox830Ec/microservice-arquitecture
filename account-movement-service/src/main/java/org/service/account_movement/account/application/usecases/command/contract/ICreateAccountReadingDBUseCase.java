package org.service.account_movement.account.application.usecases.command.contract;

import org.service.account_movement.account.domain.model.AccountDTO;

import java.util.Map;

public interface ICreateAccountReadingDBUseCase {
    AccountDTO create(AccountDTO accountDTO);
    AccountDTO create(Map<String, Object> map);
}
