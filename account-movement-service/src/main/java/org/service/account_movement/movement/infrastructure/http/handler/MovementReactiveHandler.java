package org.service.account_movement.movement.infrastructure.http.handler;

import org.service.account_movement.movement.domain.model.MovementDTO;
import org.service.account_movement.movement.infrastructure.http.service.contract.IMovementCommandReactiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class MovementReactiveHandler {
    private final IMovementCommandReactiveService commandReactiveService;

    public MovementReactiveHandler(IMovementCommandReactiveService commandReactiveService) {
        this.commandReactiveService = commandReactiveService;
    }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(MovementDTO.class)
                .flatMap(commandReactiveService::create)
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
