package org.service.account_movement.movement.domain.out.repository;

import org.service.account_movement.movement.domain.model.MovementDTO;

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
