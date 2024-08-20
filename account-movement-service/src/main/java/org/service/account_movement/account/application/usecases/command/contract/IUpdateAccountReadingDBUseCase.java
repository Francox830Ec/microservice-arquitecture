package org.service.account_movement.account.application.usecases.command.contract;

import org.service.account_movement.account.domain.model.AccountDTO;

import java.util.Map;

public interface IUpdateAccountReadingDBUseCase {
    AccountDTO update(AccountDTO accountDTO);
    AccountDTO update(Map<String, Object> map);
}
