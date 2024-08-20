package org.service.account_movement.account.domain.port.out;

import org.service.account_movement.account.domain.model.AccountDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountQueryRepository {
    List<AccountDTO> findAll();
    List<AccountDTO> findAllByState(String state);
    Optional<AccountDTO> findById(UUID uuid);
    Optional<AccountDTO> findByIdAndState(UUID uuid, String state);
    boolean existsById(UUID id);
    List<AccountDTO> findAllByClientID(UUID clientID);
}
