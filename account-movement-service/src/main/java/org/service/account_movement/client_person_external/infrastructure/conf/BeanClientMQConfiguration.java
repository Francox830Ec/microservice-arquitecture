package org.service.account_movement.client_person_external.infrastructure.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;
import org.service.account_movement.client_person_external.application.usecases.command.contract.ICreateClientReadingDBUseCase;
import org.service.account_movement.client_person_external.application.usecases.command.implementation.CreateClientReadingDBUseCaseImpl;
import org.service.account_movement.client_person_external.application.usecases.query.contract.IFindByIdClientUseCase;
import org.service.account_movement.client_person_external.application.usecases.query.contract.IFindClientByPersonIdentificationUseCase;
import org.service.account_movement.client_person_external.application.usecases.query.implementation.FindByIdClientUseCaseImpl;
import org.service.account_movement.client_person_external.application.usecases.query.implementation.FindClientByPersonIdentificationUseCaseImpl;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanClientMQConfiguration {
    @Bean
    ICreateClientReadingDBUseCase createClientUseCase(
            final IClientReadingDBRepository clientRepository,
            final ObjectMapper objectMapper,
            final IAccountQueryRepository accountQueryRepository) {
        return new CreateClientReadingDBUseCaseImpl(clientRepository, objectMapper, accountQueryRepository);
    }

    @Bean
    IFindByIdClientUseCase findClientByIdUseCase(final IClientReadingDBRepository clientRepository) {
        return new FindByIdClientUseCaseImpl(clientRepository);
    }

    @Bean
    IFindClientByPersonIdentificationUseCase findClientByPersonIdentificationUseCase(final IClientReadingDBRepository clientRepository) {
        return new FindClientByPersonIdentificationUseCaseImpl(clientRepository);
    }

}
