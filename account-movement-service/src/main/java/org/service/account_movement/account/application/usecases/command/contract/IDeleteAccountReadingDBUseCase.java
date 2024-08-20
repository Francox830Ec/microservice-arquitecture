package org.service.account_movement.account.application.usecases.command.contract;

import java.util.UUID;

public interface IDeleteAccountReadingDBUseCase {
    void deleteById(UUID id);
}
