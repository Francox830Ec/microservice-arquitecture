package org.service.account_movement.movement.domain.out.repository;

import org.service.account_movement.movement.domain.model.MovementDTO;

import java.util.Map;

public interface IMovementCommandReadingDBRepository {
    MovementDTO create(MovementDTO dto);
    MovementDTO create(Map<String, Object> map);
}