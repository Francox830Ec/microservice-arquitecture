package org.service.account_movement.movement.infrastructure.router;

import org.service.account_movement.movement.infrastructure.http.handler.MovementReactiveHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.service.account_movement.movement.infrastructure.constant.MovementConstant.BASE_PATH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class MovementReactiveRouter {
    @Bean
    public RouterFunction<ServerResponse> movementRoute(MovementReactiveHandler handler) {
        return RouterFunctions
                .route(POST(BASE_PATH)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::create);

    }
}
