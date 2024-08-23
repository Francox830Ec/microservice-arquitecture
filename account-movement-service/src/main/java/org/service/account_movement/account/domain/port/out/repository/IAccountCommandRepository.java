package org.service.account_movement.account.domain.port.out.repository;

import org.service.account_movement.account.domain.model.AccountDTO;

import java.util.UUID;

public interface IAccountCommandRepository {
    AccountDTO create(AccountDTO accountDTO);
    AccountDTO update(AccountDTO accountDTO);
    void deleteById(UUID id);
    long count();
}
