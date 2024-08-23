package org.service.account_movement.account.infrastructure.http.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.service.account_movement.account.application.usecases.query.contract.IFindAllAccountUseCase;
import org.service.account_movement.account.application.usecases.query.contract.IFindByIdAccountUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.infrastructure.http.service.contract.IAccountQueryReactiveService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class AccountQueryReactiveServiceImpl implements IAccountQueryReactiveService {
    private final IFindAllAccountUseCase findAllUseCase;
    private final IFindByIdAccountUseCase findByIdUseCase;

    public AccountQueryReactiveServiceImpl(IFindAllAccountUseCase findAllUseCase,
                                           IFindByIdAccountUseCase findByIdUseCase) {
        this.findAllUseCase = findAllUseCase;
        this.findByIdUseCase = findByIdUseCase;
    }


    @Override
    public Mono<AccountDTO> findById(UUID id) {
        return Mono.fromCallable(() -> findByIdUseCase.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(Optional::get)
                .doOnError(e -> log.error(e.getMessage(), e));
    }

    @Override
    public Flux<AccountDTO> findAll() {
        return Flux.fromIterable(findAllUseCase.findAll())
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error(e.getMessage(), e));
    }
}
