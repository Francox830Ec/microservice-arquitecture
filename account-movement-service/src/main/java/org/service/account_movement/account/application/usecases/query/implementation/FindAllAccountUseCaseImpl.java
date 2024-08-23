package org.service.account_movement.account.application.usecases.query.implementation;

import org.service.account_movement.account.application.usecases.query.contract.IFindAllAccountUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.IAccountQueryRepository;

import java.util.List;

public class FindAllAccountUseCaseImpl implements IFindAllAccountUseCase {
    private final IAccountQueryRepository repository;

    public FindAllAccountUseCaseImpl(IAccountQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AccountDTO> findAll() {
        return repository.findAll();
    }
}
