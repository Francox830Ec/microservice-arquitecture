package org.service.account_movement.movement.application.usescases.command.implementation;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.service.account_movement.account.application.usecases.exception.OperationNotFounfException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;
import org.service.account_movement.client_person_external.domain.model.ClientDTO;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;
import org.service.account_movement.movement.application.usescases.command.contract.IReplicateMovementUseCase;
import org.service.account_movement.movement.domain.model.MovementDTO;
import org.service.account_movement.movement.domain.out.repository.IMovementCommandReadingDBRepository;
import org.service.account_movement.movement.domain.out.repository.IMovementCommandRepository;
import org.service.account_movement.movement.domain.out.repository.IMovementQueryRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class ReplicateMovementUseCaseImpl implements IReplicateMovementUseCase {
    private final IMovementCommandReadingDBRepository commandReadingDBRepository;
    private final IMovementQueryRepository queryRepository;
    private final IMovementCommandRepository movementCommandRepository;
    private final IAccountQueryRepository accountQueryRepository;
    private final IAccountCommandReadingDBRepository accountCommandReadingDBRepository;
    private final IClientReadingDBRepository clientReadingDBRepository;
    private final IAccountCommandRepository accountCommandRepository;

    public ReplicateMovementUseCaseImpl(IMovementCommandReadingDBRepository commandReadingDBRepository,
                                        IMovementQueryRepository queryRepository,
                                        IMovementCommandRepository movementCommandRepository,
                                        IAccountQueryRepository accountQueryRepository,
                                        IAccountCommandReadingDBRepository accountCommandReadingDBRepository,
                                        IClientReadingDBRepository clientReadingDBRepository,
                                        IAccountCommandRepository accountCommandRepository) {
        this.commandReadingDBRepository = commandReadingDBRepository;
        this.queryRepository = queryRepository;
        this.movementCommandRepository = movementCommandRepository;
        this.accountQueryRepository = accountQueryRepository;
        this.accountCommandReadingDBRepository = accountCommandReadingDBRepository;
        this.clientReadingDBRepository = clientReadingDBRepository;
        this.accountCommandRepository = accountCommandRepository;
    }

    @Override
    public void replicateData(Map<String, Object> payload, String operation){
        switch (operation){
            case "CREATE":
                persistData(payload);
                break;
            case "UPDATE":
                break;
            case "READ": {
                if (!queryRepository.existsById(UUID.fromString(payload.get("movId").toString()))){
                    persistData(payload);
                }
                break;
            }
            default: throw new OperationNotFounfException("Operation " + operation +" not supported for replicate");
        }
    }

    private void persistData(Map<String, Object> payload){
        MovementDTO movementDTO = createdMovementReadingDB(payload);
        AccountDTO accountDTO = updateAccountMovementsListReadingDB(movementDTO);
        updateClientAccountsListReadingDB(accountDTO.clientID());
        updateMovementWritingDB(movementDTO);
        updateAccountWritingDB(accountDTO);
    }

    private void updateClientAccountsListReadingDB(UUID clientID) {
        clientReadingDBRepository.findById(clientID).ifPresent(clientDTO ->
            clientReadingDBRepository.save(
                    new ClientDTO(clientDTO.clientID(), clientDTO.clientState(), clientDTO.personName(),
                    clientDTO.personIdentification(), accountQueryRepository.findAllByClientID(clientID))));
    }

    private void updateMovementWritingDB(MovementDTO movementDTO){
        movementCommandRepository.update(movementDTO);
    }

    private void updateAccountWritingDB(AccountDTO accountDTO){
        accountCommandRepository.update(accountDTO);
    }

    private AccountDTO updateAccountMovementsListReadingDB(MovementDTO movementDTO ) {
        return accountQueryRepository.findById(movementDTO.accountId()).map(dto ->
                accountCommandReadingDBRepository.update(
                        new AccountDTO(dto.id(), dto.clientID(), dto.number(),
                        dto.type(), movementDTO.finalBalance() , dto.state(),
                        queryRepository.findAllByAccountId(dto.id())))
        ).orElseThrow((() -> new ResourceNotFoundException("Account in ReadingDB not found with id " + movementDTO.accountId())));
    }

    private MovementDTO createdMovementReadingDB(Map<String, Object> payload) {
        return getLastMovementDTO(payload).map(lastMovementDTO -> {
            payload.put("movInitialBalance", lastMovementDTO.finalBalance());
            payload.put("movFinalBalance", lastMovementDTO.finalBalance().add((BigDecimal) payload.get("movValue")));
            return commandReadingDBRepository.create(payload);
        }).orElseGet(() -> {
            payload.put("movInitialBalance", BigDecimal.valueOf(0L));
            payload.put("movFinalBalance", payload.get("movValue")) ;
            return commandReadingDBRepository.create(payload);
        });
    }

    private Optional<MovementDTO> getLastMovementDTO(Map<String, Object> payload) {
        return queryRepository.findAllByAccountId(UUID.fromString(payload.get("accId").toString()))
                .stream().filter(dto -> dto.date().isBefore(formatDateTime(payload)))
                .min((o1, o2) -> o2.date().compareTo(o1.date()));
    }

    private static LocalDateTime formatDateTime(Map<String, Object> payload) {
        return LocalDateTime.ofInstant(((Date) payload.get("movDate")).toInstant(), ZoneId.of("America/Guayaquil"));
    }
}
