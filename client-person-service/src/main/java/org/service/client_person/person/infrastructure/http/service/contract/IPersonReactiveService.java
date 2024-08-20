package org.service.client_person.person.infrastructure.http.service.contract;

import org.service.client_person.person.domain.model.PersonDTO;
import reactor.core.publisher.Mono;

public interface IPersonReactiveService {
    Mono<PersonDTO> create(PersonDTO personDTO);
}
