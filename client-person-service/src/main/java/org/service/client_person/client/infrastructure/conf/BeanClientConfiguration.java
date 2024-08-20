package org.service.client_person.client.infrastructure.conf;

import org.service.client_person.client.application.usecases.contract.*;
import org.service.client_person.client.application.usecases.implementation.*;
import org.service.client_person.client.domain.port.out.IClientRepository;
import org.service.client_person.client.domain.port.out.IProducerClientMQ;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanClientConfiguration {
    @Bean
    ICreateClientUseCase createClientUseCase(final IClientRepository repository){
        return new CreateClientUseCaseImpl(repository);
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
}
