package org.service.account_movement.movement.application.usescases.command.contract;

import org.service.account_movement.movement.domain.MovementDTO;

public interface ICreateMovementUseCase {
    MovementDTO create(MovementDTO dto);
}
