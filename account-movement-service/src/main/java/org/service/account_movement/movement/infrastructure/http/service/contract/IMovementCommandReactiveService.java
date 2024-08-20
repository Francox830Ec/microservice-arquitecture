package org.service.account_movement.movement.infrastructure.http.service.contract;

import org.service.account_movement.movement.domain.MovementDTO;
import reactor.core.publisher.Mono;

public interface IMovementCommandReactiveService {
    Mono<MovementDTO> create(MovementDTO dto);
}
