package org.service.account_movement.movement.domain.out;

import org.service.account_movement.movement.domain.MovementDTO;

import java.util.Map;

public interface IMovementCommandReadingDBRepository {
    MovementDTO create(MovementDTO dto);
    MovementDTO create(Map<String, Object> map);
}