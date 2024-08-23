package org.service.account_movement.account.application.usecases.query.implementation;

import org.service.account_movement.account.application.usecases.query.contract.IFindByIdAccountUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.IAccountQueryRepository;

import java.util.Optional;
import java.util.UUID;

public class FindByIdAccountUseCaseImpl implements IFindByIdAccountUseCase {
    private final IAccountQueryRepository repository;

    public FindByIdAccountUseCaseImpl(IAccountQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AccountDTO> findById(UUID id) {
        return repository.findById(id);
    }
}
