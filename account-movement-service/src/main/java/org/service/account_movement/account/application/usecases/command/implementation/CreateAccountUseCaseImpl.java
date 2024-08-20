package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.ICreateAccountUseCase;
import org.service.account_movement.account.application.usecases.exception.InvalidValueException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.IAccountCommandRepository;
import org.service.account_movement.movement.domain.MovementDTO;
import org.service.account_movement.movement.domain.out.IMovementCommandRepository;

import java.math.BigDecimal;

public class CreateAccountUseCaseImpl implements ICreateAccountUseCase {
    private final IAccountCommandRepository repository;
    private final IMovementCommandRepository movementCommandRepository;

    public CreateAccountUseCaseImpl(IAccountCommandRepository repository,
                                    IMovementCommandRepository movementCommandRepository) {
        this.repository = repository;
        this.movementCommandRepository = movementCommandRepository;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        if (accountDTO.initialBalance() == null || accountDTO.initialBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidValueException("Account initialBalance must be a positive value");
        }

        return createMovement(repository.create(accountDTO));
    }

    private AccountDTO createMovement(AccountDTO accountResponseDTO) {
        movementCommandRepository.create(new MovementDTO(null, accountResponseDTO.id(), null, "Apertura de Cuenta",
                accountResponseDTO.initialBalance(), accountResponseDTO.initialBalance(), BigDecimal.valueOf(0L)));

        return accountResponseDTO;
    }
}
