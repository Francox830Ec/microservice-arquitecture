package org.service.client_person.client.infrastructure.http.service.contract;

import org.service.client_person.client.domain.model.ClientDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IClientCRUDReactiveService {
    Mono<ClientDTO> create(ClientDTO clientDTO);
    Mono<ClientDTO> update(UUID id, ClientDTO clientRequestDTO);
    Mono<Void> delete(UUID id);
    Mono<ClientDTO> findById(UUID id);
    Flux<ClientDTO> findAll();
}
