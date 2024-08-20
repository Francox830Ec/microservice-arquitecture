package org.service.account_movement.client_person_external.infrastructure.http.handler;

import org.service.account_movement.client_person_external.infrastructure.http.service.contract.IReportQueryReactiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ReportReactiveHandler {
    private final IReportQueryReactiveService service;

    public ReportReactiveHandler(IReportQueryReactiveService service) {
        this.service = service;
    }

    public Mono<ServerResponse> report(ServerRequest request){
        return service.reporte(request.pathVariable("personIdentification"), request.queryParams().toSingleValueMap())
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
