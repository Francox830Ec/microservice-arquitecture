package org.service.account_movement.movement.infrastructure.http.service.contract;

import jakarta.validation.Valid;
import org.service.account_movement.movement.domain.model.MovementDTO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Validated
public interface IMovementCommandReactiveService {
    Mono<MovementDTO> create(@Valid MovementDTO dto);
}
