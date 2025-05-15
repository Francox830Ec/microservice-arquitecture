package org.service.client_person.client.infrastructure.http.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.service.client_person.client.application.usecases.contract.*;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.infrastructure.http.service.contract.IClientCRUDReactiveService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class ClientCRUDReactiveServiceImpl implements IClientCRUDReactiveService {
    private final ICreateClientUseCase createUseCase;
    private final IUpdateClientByIdUseCase updateByIdUseCase;
    private final IDeleteClientByIdUseCase deleteByIdUseCase;
    private final IFindByIdClientUseCase findByIdUseCase;
    private final IFindAllClientUseCase findAllUseCase;
    private final ISendClientMQUseCase sendMQUseCase;

    public ClientCRUDReactiveServiceImpl(ICreateClientUseCase createUseCase,
            IUpdateClientByIdUseCase updateByIdUseCase,
            IDeleteClientByIdUseCase deleteByIdUseCase,
            IFindByIdClientUseCase findByIdUseCase,
            IFindAllClientUseCase findAllUseCase,
            ISendClientMQUseCase sendMQUseCase) {
        this.createUseCase = createUseCase;
        this.updateByIdUseCase = updateByIdUseCase;
        this.deleteByIdUseCase = deleteByIdUseCase;
        this.findByIdUseCase = findByIdUseCase;
        this.findAllUseCase = findAllUseCase;
        this.sendMQUseCase = sendMQUseCase;
    }

    @Override
    public Mono<ClientDTO> create(ClientDTO clientRequest) {
        return Mono.fromCallable(() -> createUseCase.create(clientRequest))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(clientResponseDTO -> sendMQUseCase.sendPayloadMQ(clientResponseDTO, "create"))
                .doOnError(e -> log.error(e.getMessage(), e));
    }

    @Override
    public Mono<ClientDTO> update(UUID id, ClientDTO clientRequestDTO) {
        return Mono.fromCallable(() -> updateByIdUseCase.updateById(id, clientRequestDTO))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(clientResponseDTO -> sendMQUseCase.sendPayloadMQ(clientResponseDTO, "update"))
                .doOnError(e -> log.error(e.getMessage(), e));
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return Mono.fromCallable(() -> deleteByIdUseCase.deleteLogicalById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(clientResponseDTO -> sendMQUseCase.sendPayloadMQ(clientResponseDTO, "update"))
                .doOnError(e -> log.error(e.getMessage(), e))
                .then();
    }

    @Override
    public Mono<ClientDTO> findById(UUID id) {
        return Mono.fromCallable(() -> findByIdUseCase.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(Optional::get)
                .doOnError(e -> log.error(e.getMessage(), e));
    }

    @Override
    public Flux<ClientDTO> findAll() {
        return Flux.fromIterable(findAllUseCase.findAll())
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error(e.getMessage(), e));
    }
}