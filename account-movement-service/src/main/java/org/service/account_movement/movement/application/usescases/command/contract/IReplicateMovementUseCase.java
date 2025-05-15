package org.service.account_movement.movement.application.usescases.command.contract;

import java.util.Map;

public interface IReplicateMovementUseCase {
    void replicateData(Map<String, Object> payload, String operation);
}
