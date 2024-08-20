package org.service.account_movement.account.infrastructure.adapter.out;

import org.service.account_movement.account.infrastructure.hash.AccountHash;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountRedisRepository extends CrudRepository<AccountHash, UUID> {
    List<AccountHash> findAllByState(String state);
    Optional<AccountHash> findByIdAndState(UUID uuid, String state);
    List<AccountHash> findAllByClientID(UUID clientID);
}