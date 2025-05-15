package org.service.client_person.person.infrastructure.router;

import org.service.client_person.person.infrastructure.http.handler.PersonReactiveHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class PersonReactiveRouter {
    @Bean
    public RouterFunction<ServerResponse> personRoute(PersonReactiveHandler handler) {
        return RouterFunctions
                .route(POST("/person")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::create);
    }
}
