package org.service.account_movement.movement.infrastructure.http.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.service.account_movement.movement.application.usescases.command.contract.ICreateMovementUseCase;
import org.service.account_movement.movement.domain.model.MovementDTO;
import org.service.account_movement.movement.infrastructure.http.service.contract.IMovementCommandReactiveService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Log4j2
@Service
public class MovementCommandReactiveServiceImpl implements IMovementCommandReactiveService {
    private final ICreateMovementUseCase createUseCase;

    public MovementCommandReactiveServiceImpl(ICreateMovementUseCase createMovementUseCase) {
        this.createUseCase = createMovementUseCase;
    }

    @Override
    public Mono<MovementDTO> create(MovementDTO dto) {
        return Mono.fromCallable(() -> createUseCase.create(dto))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error(e.getMessage(), e));
    }
}
