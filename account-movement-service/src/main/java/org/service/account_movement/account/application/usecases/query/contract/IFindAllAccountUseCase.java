package org.service.account_movement.account.application.usecases.query.contract;

import org.service.account_movement.account.domain.model.AccountDTO;

import java.util.List;

public interface IFindAllAccountUseCase {
    List<AccountDTO> findAll();
}
