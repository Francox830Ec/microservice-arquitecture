package org.service.client_person.person.infrastructure.http.handler;

import org.service.client_person.person.domain.model.PersonDTO;
import org.service.client_person.person.infrastructure.http.service.contract.IPersonReactiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class PersonReactiveHandler {
    private final IPersonReactiveService personReactiveService;

    public PersonReactiveHandler(IPersonReactiveService personReactiveService) {
        this.personReactiveService = personReactiveService;
    }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(PersonDTO.class)
                .flatMap(personReactiveService::create)
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
