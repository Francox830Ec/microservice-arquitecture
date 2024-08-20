package org.service.client_person.client.infrastructure.router;

import org.service.client_person.client.infrastructure.http.handler.ClientReactiveHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.service.client_person.client.infrastructure.constant.ClientConstant.BASE_PATH;
import static org.service.client_person.client.infrastructure.constant.ClientConstant.ID_PARAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class ClientReactiveRouter {
    @Bean
    public RouterFunction<ServerResponse> clientRoute(ClientReactiveHandler handler) {
        return RouterFunctions
                .route(POST(BASE_PATH)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(PUT(BASE_PATH + ID_PARAM)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::update)
                .andRoute(DELETE(BASE_PATH + ID_PARAM)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::delete)
                .andRoute(GET(BASE_PATH + ID_PARAM)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(GET(BASE_PATH)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::findAll);
    }
}
