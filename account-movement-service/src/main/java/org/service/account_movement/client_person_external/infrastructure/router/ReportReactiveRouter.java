package org.service.account_movement.client_person_external.infrastructure.router;

import org.service.account_movement.client_person_external.infrastructure.http.handler.ReportReactiveHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class ReportReactiveRouter {
    @Bean
    public RouterFunction<ServerResponse> reportRoute(ReportReactiveHandler handler) {
        return RouterFunctions
                .route(GET("/clientes/{personIdentification}/movimientos/reportes")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::report);
    }
}
