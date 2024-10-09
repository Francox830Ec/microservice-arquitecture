package org.service.client_person.client.infrastructure.http.exception;

import lombok.extern.log4j.Log4j2;
import org.service.client_person.client.domain.model.ErrorResponse;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Log4j2
public class ReactiveExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode;
    private final HttpStatus defaultStatus;
    /**
     * Create a new {@code AbstractErrorWebExceptionHandler}.
     *
     * @param errorAttributes    the error attributes
     * @param resources          the resources configuration properties
     * @param applicationContext the application context
     * @since 2.4.0
     */
    public ReactiveExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                                    ApplicationContext applicationContext,
                                    Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode, HttpStatus defaultStatus) {
        super(errorAttributes, resources, applicationContext);
        this.exceptionToStatusCode = exceptionToStatusCode;
        this.defaultStatus = defaultStatus;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = getError(request);
        log.error("An error has been occurred", error);
        HttpStatus httpStatus;
        if (error instanceof Exception exception) {
            httpStatus = exceptionToStatusCode.getOrDefault(exception.getClass(), defaultStatus);
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ErrorResponse
                        .builder()
                        .code(httpStatus.value())
                        .message(error.getMessage())
                        .build())
                );
    }
}
