package org.service.account_movement.account.application.usecases.query.implementation;

import org.service.account_movement.account.application.usecases.query.contract.IFindByIdAndStateAccountUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;

import java.util.Optional;
import java.util.UUID;

public class FindByIdAndStateAccountUseCaseImpl implements IFindByIdAndStateAccountUseCase {
    private final IAccountQueryRepository repository;

    public FindByIdAndStateAccountUseCaseImpl(IAccountQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AccountDTO> findByIdAndState(UUID id, Boolean state) {
        return repository.findByIdAndState(id, state);
    }
}
