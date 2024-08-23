package org.service.account_movement.client_person_external.infrastructure.adapter.out.redis;

import org.service.account_movement.account.infrastructure.hash.ClientHash;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface IClientRedisRepository extends CrudRepository<ClientHash, UUID> {
    Optional<ClientHash> findByPersonIdentification(String personIdentification);
}
