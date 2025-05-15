package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.IDeleteAccountReadingDBUseCase;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandReadingDBRepository;

import java.util.UUID;

public class DeleteAccountReadingDBUseCaseImpl implements IDeleteAccountReadingDBUseCase {
    private final IAccountCommandReadingDBRepository repository;

    public DeleteAccountReadingDBUseCaseImpl(IAccountCommandReadingDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
