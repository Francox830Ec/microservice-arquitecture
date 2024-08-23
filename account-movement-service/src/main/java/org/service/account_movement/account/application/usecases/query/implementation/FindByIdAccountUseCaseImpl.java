package org.service.account_movement.account.application.usecases.query.implementation;

import org.service.account_movement.account.application.usecases.exception.RecursoNotFoundException;
import org.service.account_movement.account.application.usecases.query.contract.IFindByIdAccountUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;

import java.util.Optional;
import java.util.UUID;

public class FindByIdAccountUseCaseImpl implements IFindByIdAccountUseCase {
    private final IAccountQueryRepository repository;

    public FindByIdAccountUseCaseImpl(IAccountQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AccountDTO> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id).map(accountDTO ->
                        new AccountDTO(accountDTO.id(), accountDTO.clientID(), accountDTO.number(),
                                accountDTO.type(), accountDTO.initialBalance(), accountDTO.state(),
                                accountDTO.movements().stream().sorted((o1, o2) -> o2.date().compareTo(o1.date())).toList()))
                .orElseThrow(() -> new RecursoNotFoundException("Account with id " + id + " not found")));
    }
}
