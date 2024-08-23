package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.ICreateAccountReadingDBUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandReadingDBRepository;

import java.util.Map;

public class CreateAccountReadingDBUseCaseImpl implements ICreateAccountReadingDBUseCase {
    private final IAccountCommandReadingDBRepository repository;

    public CreateAccountReadingDBUseCaseImpl(IAccountCommandReadingDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        return repository.create(accountDTO);
    }

    @Override
    public AccountDTO create(Map<String, Object> map) {
        return repository.create(map);
    }
}
