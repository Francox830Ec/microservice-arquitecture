package org.service.client_person.person.infrastructure.http.service.contract;

import jakarta.validation.Valid;
import org.service.client_person.person.domain.model.PersonDTO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Validated
public interface IPersonReactiveService {
    Mono<PersonDTO> create(@Valid PersonDTO personDTO);
}
