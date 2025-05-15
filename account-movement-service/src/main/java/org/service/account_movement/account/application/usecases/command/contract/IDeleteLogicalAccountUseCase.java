package org.service.account_movement.account.application.usecases.command.contract;

import java.util.UUID;

public interface IDeleteLogicalAccountUseCase {
    void deleteById(UUID id);
}
