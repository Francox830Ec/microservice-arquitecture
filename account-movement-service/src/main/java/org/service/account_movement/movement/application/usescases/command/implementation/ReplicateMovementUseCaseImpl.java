package org.service.account_movement.movement.application.usescases.command.implementation;

import org.service.account_movement.account.application.usecases.exception.OperationNotFounfException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.model.ClientDTO;
import org.service.account_movement.account.domain.port.out.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.domain.port.out.IAccountQueryRepository;
import org.service.account_movement.client_person_external.domain.port.out.IClientReadingDBRepository;
import org.service.account_movement.movement.application.usescases.command.contract.IReplicateMovementUseCase;
import org.service.account_movement.movement.domain.MovementDTO;
import org.service.account_movement.movement.domain.out.IMovementCommandReadingDBRepository;
import org.service.account_movement.movement.domain.out.IMovementCommandRepository;
import org.service.account_movement.movement.domain.out.IMovementQueryRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ReplicateMovementUseCaseImpl implements IReplicateMovementUseCase {
    private final IMovementCommandReadingDBRepository commandReadingDBRepository;
    private final IMovementQueryRepository queryRepository;
    private final IMovementCommandRepository movementCommandRepository;
    private final IAccountQueryRepository accountQueryRepository;
    private final IAccountCommandReadingDBRepository accountCommandReadingDBRepository;
    private final IClientReadingDBRepository clientReadingDBRepository;

    public ReplicateMovementUseCaseImpl(IMovementCommandReadingDBRepository commandReadingDBRepository,
                                        IMovementQueryRepository queryRepository,
                                        IMovementCommandRepository movementCommandRepository,
                                        IAccountQueryRepository accountQueryRepository,
                                        IAccountCommandReadingDBRepository accountCommandReadingDBRepository,
                                        IClientReadingDBRepository clientReadingDBRepository) {
        this.commandReadingDBRepository = commandReadingDBRepository;
        this.queryRepository = queryRepository;
        this.movementCommandRepository = movementCommandRepository;
        this.accountQueryRepository = accountQueryRepository;
        this.accountCommandReadingDBRepository = accountCommandReadingDBRepository;
        this.clientReadingDBRepository = clientReadingDBRepository;
    }

    @Override
    public void replicateData(Map<String, Object> payload, String operation){
        switch (operation){
            case "CREATE", "UPDATE":
                MovementDTO movementDTO = createdMovementReadingDB(payload);
                movementCommandRepository.update(movementDTO);
                Optional<AccountDTO> accountDTO = updateAccountReadingDB(movementDTO);
                updateClientReadingDB(accountDTO);
                break;
            case "READ": {
                if (!queryRepository.existsById(UUID.fromString(payload.get("movId").toString()))){
                    commandReadingDBRepository.create(payload);
                }
                break;
            }
            default: throw new OperationNotFounfException("Operation " + operation +" not supported");
        }
    }

    private void updateClientReadingDB(Optional<AccountDTO> accountDTO) {
        ClientDTO clientDTO = clientReadingDBRepository.findById(accountDTO.get().clientID()).get();
        List<AccountDTO> accountsByClientList = accountQueryRepository.findAllByClientID(accountDTO.get().clientID());
        clientReadingDBRepository.save(new ClientDTO(clientDTO.clientID(), clientDTO.clientState(), clientDTO.personName(),
                clientDTO.personIdentification(), accountsByClientList));
    }

    private Optional<AccountDTO> updateAccountReadingDB(MovementDTO movementDTO) {
        List<MovementDTO> movementsByAccountList = queryRepository.findAllByAccountId(movementDTO.accountId());
        Optional<AccountDTO> accountDTO = accountQueryRepository.findById(movementDTO.accountId());
        accountCommandReadingDBRepository.update(new AccountDTO(accountDTO.get().id(), accountDTO.get().clientID(),
                accountDTO.get().number(),accountDTO.get().type(), accountDTO.get().initialBalance(),
                accountDTO.get().state(),
                movementsByAccountList));

        return accountDTO;
    }

    private MovementDTO createdMovementReadingDB(Map<String, Object> payload) {
        LocalDateTime date = LocalDateTime.ofInstant(((Date) payload.get("movDate")).toInstant(),
                ZoneId.of("America/Guayaquil"));
        UUID accId = UUID.fromString(payload.get("accId").toString());

        Optional<MovementDTO> lastMovementDTO = queryRepository.findAllByAccountId(accId)
                .stream().filter(dto -> dto.date().isBefore(date))
                .min((o1, o2) -> o2.date().compareTo(o1.date()));

        if (lastMovementDTO.isEmpty()) {
            payload.put("movInitialBalance", BigDecimal.valueOf(0L));
            payload.put("movFinalBalance", payload.get("movValue")) ;
        }else {
            payload.put("movInitialBalance", lastMovementDTO.get().finalBalance());
            payload.put("movFinalBalance", lastMovementDTO.get().finalBalance().
                    add((BigDecimal) payload.get("movValue")));
        }

        return commandReadingDBRepository.create(payload);
    }
}
