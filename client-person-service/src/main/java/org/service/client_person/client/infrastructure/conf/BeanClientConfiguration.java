package org.service.client_person.client.infrastructure.conf;

import org.service.client_person.client.application.usecases.contract.*;
import org.service.client_person.client.application.usecases.exception.RecursoNotFoundException;
import org.service.client_person.client.application.usecases.implementation.*;
import org.service.client_person.client.domain.port.out.messages.IProducerClientMQ;
import org.service.client_person.client.domain.port.out.repository.IClientRepository;
import org.service.client_person.client.infrastructure.http.exception.ReactiveExceptionHandler;
import org.service.client_person.person.domain.port.out.repository.IPersonRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Map;

@Configuration
@EnableWebFlux
@EnableConfigurationProperties(WebProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class BeanClientConfiguration {
    @Bean
    ICreateClientUseCase createClientUseCase(final IClientRepository repository, final IPersonRepository personRepository){
        return new CreateClientUseCaseImpl(repository, personRepository);
    }

    @Bean
    IUpdateClientByIdUseCase updateClientByIdUseCase(final IClientRepository repository){
        return new UpdateClientByIdUseCaseImpl(repository);
    }

    @Bean
    IDeleteClientByIdUseCase deleteClientByIdUseCase(final IClientRepository repository){
        return new DeleteClientByIdUseCaseImpl(repository);
    }

    @Bean
    IFindByIdClientUseCase findClientByIdUseCase(final IClientRepository repository){
        return new FindByIdClientUseCaseImpl(repository);
    }

    @Bean
    IFindAllClientUseCase findAllClientUseCase(final IClientRepository repository){
        return new FindAllClientUseCaseImpl(repository);
    }

    @Bean
    ISendClientMQUseCase sendClientMQUseCase(final IProducerClientMQ producerClientMQ){
        return new SendClientMQUseCaseImpl(producerClientMQ);
    }

    @Bean
    @Order(-2)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public ReactiveExceptionHandler reactiveExceptionHandler(WebProperties webProperties, ApplicationContext applicationContext,
                                                             ServerCodecConfigurer configurer) {
        ReactiveExceptionHandler exceptionHandler = new ReactiveExceptionHandler(
                new DefaultErrorAttributes(), webProperties.getResources(), applicationContext, exceptionToStatusCode(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        exceptionHandler.setMessageWriters(configurer.getWriters());
        exceptionHandler.setMessageReaders(configurer.getReaders());
        return exceptionHandler;
    }


    @Bean
    public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
        return Map.of(
                RecursoNotFoundException.class, HttpStatus.BAD_REQUEST
        );
    }
}
