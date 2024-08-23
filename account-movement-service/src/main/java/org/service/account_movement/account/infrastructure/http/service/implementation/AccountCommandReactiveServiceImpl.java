package org.service.account_movement.account.infrastructure.http.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.service.account_movement.account.application.usecases.command.contract.ICreateAccountReadingDBUseCase;
import org.service.account_movement.account.application.usecases.command.contract.ICreateAccountUseCase;
import org.service.account_movement.account.application.usecases.command.contract.IDeleteLogicalAccountUseCase;
import org.service.account_movement.account.application.usecases.command.contract.IUpdateAccountUseCase;
import org.service.account_movement.account.application.usecases.query.contract.IFindByIdAndStateAccountUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.infrastructure.http.service.contract.IAccountCommandReactiveService;
import org.service.account_movement.movement.application.usescases.command.contract.ICreateMovementUseCase;
import org.service.account_movement.movement.domain.model.MovementDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Log4j2
public class AccountCommandReactiveServiceImpl implements IAccountCommandReactiveService {
    private final ICreateAccountUseCase createUseCase;
    private final IUpdateAccountUseCase updateUseCase;
    private final IDeleteLogicalAccountUseCase deleteUseCase;
    private final ICreateMovementUseCase createMovementUseCase;
    private final ICreateAccountReadingDBUseCase createAccountReadingDBUseCase;
    private final IFindByIdAndStateAccountUseCase findByIdAndStateAccountReadingDBUseCase;

    public AccountCommandReactiveServiceImpl(ICreateAccountUseCase createUseCase,
                                             IUpdateAccountUseCase updateUseCase,
                                             IDeleteLogicalAccountUseCase deleteUseCase,
                                             ICreateMovementUseCase createMovementUseCase,
                                             ICreateAccountReadingDBUseCase createAccountReadingDBUseCase,
                                             IFindByIdAndStateAccountUseCase findByIdAndStateAccountReadingDBUseCase) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.createMovementUseCase = createMovementUseCase;
        this.createAccountReadingDBUseCase = createAccountReadingDBUseCase;
        this.findByIdAndStateAccountReadingDBUseCase = findByIdAndStateAccountReadingDBUseCase;
    }

    @Override
    public Mono<AccountDTO> create(AccountDTO requestDTO) {
        return Mono.fromCallable(() -> createUseCase.create(requestDTO))
                .doOnNext(savedAccountDTO ->
                    findByIdAndStateAccountReadingDBUseCase.findByIdAndState(savedAccountDTO.id(), savedAccountDTO.state())
                            .orElseGet(() -> createAccountReadingDBUseCase.create(savedAccountDTO)))
                .doOnNext(savedAccountDTO -> createMovementUseCase.create(
                        new MovementDTO(null, savedAccountDTO.id(),null, "Apertura de Cuenta",
                                BigDecimal.valueOf(0L), savedAccountDTO.initialBalance(),savedAccountDTO.initialBalance())))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error(e.getMessage(), e));
    }

    @Override
    public Mono<AccountDTO> update(UUID id, AccountDTO accountDTO) {
        return Mono.fromCallable(() -> updateUseCase.updateById(id, accountDTO))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error(e.getMessage(), e));
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return Mono.fromRunnable(() -> deleteUseCase.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error(e.getMessage(), e))
                .then();
    }
}
