package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.ICreateAccountUseCase;
import org.service.account_movement.account.application.usecases.exception.InvalidValueException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.IAccountCommandRepository;

import java.math.BigDecimal;

public class CreateAccountUseCaseImpl implements ICreateAccountUseCase {
    private final IAccountCommandRepository repository;

    public CreateAccountUseCaseImpl(IAccountCommandRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        validateDTO(accountDTO);
        return repository.create(accountDTO);
    }

    private static void validateDTO(AccountDTO accountDTO) {
        if (accountDTO.initialBalance() == null || accountDTO.initialBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidValueException("Account initialBalance must be a positive value");
        }
    }
}
