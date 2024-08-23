package org.service.account_movement.movement.domain.out;

import org.service.account_movement.movement.domain.MovementDTO;

public interface IMovementCommandRepository {
    MovementDTO create(MovementDTO dto);
    MovementDTO update(MovementDTO dto);
    void deleteById(Long id);
}
