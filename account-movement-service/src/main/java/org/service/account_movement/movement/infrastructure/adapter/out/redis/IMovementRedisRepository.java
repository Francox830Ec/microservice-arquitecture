package org.service.account_movement.movement.infrastructure.adapter.out.redis;

import org.service.account_movement.movement.infrastructure.hash.MovementHash;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMovementRedisRepository extends CrudRepository<MovementHash, UUID> {
    List<MovementHash> findAllByAccountId(UUID accountId);

    @Query(value = "select m from MovementHash m where m.accountId = ?1 and m.date <= ?2", nativeQuery = true)
    Optional<MovementHash> findFirstByAccountIdAndDate(@Param("accountId") UUID accountId, @Param("date") LocalDateTime date);

    Optional<MovementHash> findTopByDateLessThanOrderByDateDesc(LocalDateTime date);

    Optional<MovementHash> findTopByAccountIdOrderByDateDesc(UUID accountId);
}