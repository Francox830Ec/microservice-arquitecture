package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.IReplicateAccountUseCase;
import org.service.account_movement.account.application.usecases.exception.OperationNotFounfException;
import org.service.account_movement.account.domain.port.out.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.domain.port.out.IAccountQueryRepository;

import java.util.Map;
import java.util.UUID;

public class ReplicateAccountUseCaseImpl implements IReplicateAccountUseCase {
    private final IAccountCommandReadingDBRepository commandReadingDBRepository;
    private final IAccountQueryRepository queryRepository;

    public ReplicateAccountUseCaseImpl(IAccountCommandReadingDBRepository commandRepository,
                                       IAccountQueryRepository queryRepository) {
        this.commandReadingDBRepository = commandRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    public void replicateData(Map<String, Object> payload, String operation) {
        switch (operation){
            case "CREATE", "UPDATE":
                commandReadingDBRepository.create(payload);
                break;
            case "READ": {
                if (!queryRepository.existsById(UUID.fromString(payload.get("accId").toString()))){
                    commandReadingDBRepository.create(payload);
                }
                break;
            }
            case "DELETE":
                commandReadingDBRepository.deleteById(UUID.fromString(payload.get("accId").toString()));
                break;
            default: throw new OperationNotFounfException("Operation " + operation +" not supported");
        }
    }
}
