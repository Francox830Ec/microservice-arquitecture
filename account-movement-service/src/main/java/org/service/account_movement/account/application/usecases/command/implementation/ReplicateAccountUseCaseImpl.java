package org.service.account_movement.account.application.usecases.command.implementation;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.service.account_movement.account.application.usecases.command.contract.IReplicateAccountUseCase;
import org.service.account_movement.account.application.usecases.exception.OperationNotFounfException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;

import java.math.BigDecimal;
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
            case "CREATE":
                commandReadingDBRepository.create(payload);
                break;
            case "UPDATE":
                commandReadingDBRepository.update(buildAccountDTO(payload));
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
            default: throw new OperationNotFounfException("Operation " + operation +" not supported for replicate");
        }
    }

    private AccountDTO buildAccountDTO(Map<String, Object> payload) {
        return queryRepository.findById(UUID.fromString(payload.get("accId").toString())).map(accountDTO ->
             new AccountDTO(
                    UUID.fromString(payload.get("accId").toString()),
                    UUID.fromString(payload.get("cliId").toString()),
                    payload.get("accNumber").toString(),
                    payload.get("accType").toString(),
                    (BigDecimal) payload.get("accInitialBalance"),
                    (Boolean) payload.get("accState"),
                    accountDTO.movements()
            )
        ).orElseThrow(() -> new ResourceNotFoundException("Account in ReadingDB not found with id " + payload.get("accId").toString()));
    }
}
