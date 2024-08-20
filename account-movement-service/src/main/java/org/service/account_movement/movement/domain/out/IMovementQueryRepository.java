package org.service.account_movement.movement.domain.out;

import org.service.account_movement.movement.domain.MovementDTO;
import org.service.account_movement.movement.infrastructure.hash.MovementHash;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMovementQueryRepository {
    List<MovementDTO> findAll();
    Optional<MovementDTO> findById(UUID id);
    boolean existsById(UUID id);
    List<MovementDTO> findAllByAccountId(UUID accountId);
    Optional<MovementDTO> findFirstByAccountIdAndDateOrderByDateDesc(UUID accountId, LocalDateTime date);
    Optional<MovementDTO> findTopByDateLessThanOrderByDateDesc(LocalDateTime date);
    Optional<MovementDTO> findTopByAccountIdOrderByDateDesc(UUID accountId);
}
