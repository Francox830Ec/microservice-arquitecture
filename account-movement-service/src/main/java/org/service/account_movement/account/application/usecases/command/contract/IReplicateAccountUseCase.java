package org.service.account_movement.account.application.usecases.command.contract;

import java.util.Map;

public interface IReplicateAccountUseCase {
    void replicateData(Map<String, Object> payload, String operation);
}
