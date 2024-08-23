package org.service.account_movement.account.infrastructure.adapter.out.redis;

import org.service.account_movement.account.infrastructure.hash.AccountHash;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountRedisRepository extends CrudRepository<AccountHash, UUID> {
    List<AccountHash> findAllByState(Boolean state);
    Optional<AccountHash> findByIdAndState(UUID uuid, Boolean state);
    List<AccountHash> findAllByClientID(UUID clientID);
}