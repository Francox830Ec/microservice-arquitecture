package org.service.account_movement.account.infrastructure.router;

import org.service.account_movement.account.infrastructure.http.handler.AccountReactiveHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.service.account_movement.account.infrastructure.constant.AccountConstant.BASE_PATH;
import static org.service.account_movement.account.infrastructure.constant.AccountConstant.ID_PARAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class AccountReactiveRouter {
    @Bean
    public RouterFunction<ServerResponse> accountRoute(AccountReactiveHandler handler) {
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
