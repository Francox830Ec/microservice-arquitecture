package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.IUpdateAccountReadingDBUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandReadingDBRepository;

import java.util.Map;

public class UpdateAccountReadingDBUseCaseImpl implements IUpdateAccountReadingDBUseCase {
    private final IAccountCommandReadingDBRepository repository;

    public UpdateAccountReadingDBUseCaseImpl(IAccountCommandReadingDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        return repository.update(accountDTO);
    }

    @Override
    public AccountDTO update(Map<String, Object> map) {
        return repository.update(map);
    }
}
