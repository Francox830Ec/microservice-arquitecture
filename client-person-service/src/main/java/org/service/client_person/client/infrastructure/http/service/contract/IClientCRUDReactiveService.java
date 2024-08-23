package org.service.client_person.client.infrastructure.http.service.contract;

import jakarta.validation.Valid;
import org.service.client_person.client.domain.model.ClientDTO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Validated
public interface IClientCRUDReactiveService {
    Mono<ClientDTO> create(@Valid ClientDTO clientDTO);
    Mono<ClientDTO> update(UUID id, @Valid ClientDTO clientRequestDTO);
    Mono<Void> delete(UUID id);
    Mono<ClientDTO> findById(UUID id);
    Flux<ClientDTO> findAll();
}
