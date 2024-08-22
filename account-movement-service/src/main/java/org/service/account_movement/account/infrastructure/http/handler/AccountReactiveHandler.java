package org.service.account_movement.account.infrastructure.http.handler;

import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.infrastructure.http.service.contract.IAccountCommandReactiveService;
import org.service.account_movement.account.infrastructure.http.service.contract.IAccountQueryReactiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class AccountReactiveHandler {
    private final IAccountCommandReactiveService commandReactiveService;
    private final IAccountQueryReactiveService queryReactiveService;

    public AccountReactiveHandler(IAccountCommandReactiveService commandReactiveService,
                                  IAccountQueryReactiveService queryReactiveService) {
        this.commandReactiveService = commandReactiveService;
        this.queryReactiveService = queryReactiveService;
    }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(AccountDTO.class)
                .flatMap(commandReactiveService::create)
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(AccountDTO.class)
                .flatMap(dto -> commandReactiveService.update(UUID.fromString(request.pathVariable("uuid")), dto))
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        return commandReactiveService.delete(UUID.fromString(request.pathVariable("uuid")))
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        return queryReactiveService.findById(UUID.fromString(request.pathVariable("uuid")))
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return queryReactiveService.findAll()
                .collectList()
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
