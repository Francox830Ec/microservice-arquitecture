package org.service.account_movement.account.domain.port.out.repository;

import org.service.account_movement.account.domain.model.AccountDTO;

import java.util.Map;
import java.util.UUID;

public interface IAccountCommandReadingDBRepository {
    AccountDTO create(AccountDTO accountDTO);
    AccountDTO create(Map<String, Object> map);
    AccountDTO update(AccountDTO accountDTO);
    AccountDTO update(Map<String, Object> map);
    void deleteById(UUID id);
}
