package org.service.client_person.client.infrastructure.http.handler;

import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.infrastructure.http.service.contract.IClientCRUDReactiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ClientReactiveHandler {
    private final IClientCRUDReactiveService service;

    public ClientReactiveHandler(IClientCRUDReactiveService service) {
        this.service = service;
    }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(ClientDTO.class)
                .flatMap(service::create)
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)));
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(ClientDTO.class)
                .flatMap(dto -> service.update(UUID.fromString(request.pathVariable("uuid")), dto))
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)));
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        return service.delete(UUID.fromString(request.pathVariable("uuid")))
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        return service.findById(UUID.fromString(request.pathVariable("uuid")))
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)));
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return service.findAll()
                .collectList()
                .flatMap(response -> {
                    request.queryParam("");
                    return  ServerResponse
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(response));

                });
    }
}