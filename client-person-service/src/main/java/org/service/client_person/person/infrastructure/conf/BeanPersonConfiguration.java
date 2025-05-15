package org.service.client_person.person.infrastructure.conf;

import org.service.client_person.person.application.usecases.command.contract.ICreatePersonUseCase;
import org.service.client_person.person.application.usecases.command.implementation.CreatePersonUseCaseImpl;
import org.service.client_person.person.domain.port.out.repository.IPersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPersonConfiguration {
    @Bean
    ICreatePersonUseCase createPersonUseCase(final IPersonRepository personRepository){
        return new CreatePersonUseCaseImpl(personRepository);
    }
}
