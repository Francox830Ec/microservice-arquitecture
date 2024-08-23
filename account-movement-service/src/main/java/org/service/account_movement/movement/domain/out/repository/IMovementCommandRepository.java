package org.service.account_movement.movement.domain.out.repository;

import org.service.account_movement.movement.domain.model.MovementDTO;

public interface IMovementCommandRepository {
    MovementDTO create(MovementDTO dto);
    MovementDTO update(MovementDTO dto);
    void deleteById(Long id);
}
