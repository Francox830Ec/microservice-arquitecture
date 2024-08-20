package org.service.client_person.person.infrastructure.http.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.service.client_person.person.application.usecases.command.contract.ICreatePersonUseCase;
import org.service.client_person.person.domain.model.PersonDTO;
import org.service.client_person.person.infrastructure.http.service.contract.IPersonReactiveService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Log4j2
@Service
public class PersonReactiveServiceImpl implements IPersonReactiveService {
    private final ICreatePersonUseCase createPersonUseCase;

    public PersonReactiveServiceImpl(ICreatePersonUseCase createPersonUseCase) {
        this.createPersonUseCase = createPersonUseCase;
    }

    @Override
    public Mono<PersonDTO> create(PersonDTO personDTO) {
        return Mono.fromCallable(() -> createPersonUseCase
                .create(personDTO))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error(e.getMessage(), e));
    }
}
