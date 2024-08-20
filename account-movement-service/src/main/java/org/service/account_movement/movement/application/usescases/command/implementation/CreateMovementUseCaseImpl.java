package org.service.account_movement.movement.application.usescases.command.implementation;

import org.service.account_movement.account.application.usecases.exception.InvalidValueException;
import org.service.account_movement.movement.application.usescases.command.contract.ICreateMovementUseCase;
import org.service.account_movement.movement.domain.MovementDTO;
import org.service.account_movement.movement.domain.out.IMovementCommandRepository;
import org.service.account_movement.movement.domain.out.IMovementQueryRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class CreateMovementUseCaseImpl implements ICreateMovementUseCase {
    private final IMovementCommandRepository repository;
    private final IMovementQueryRepository queryRepository;

    public CreateMovementUseCaseImpl(IMovementCommandRepository repository,
                                     IMovementQueryRepository queryRepository) {
        this.repository = repository;
        this.queryRepository = queryRepository;
    }

    @Override
    public MovementDTO create(MovementDTO dto) {
        validate(dto);
        return repository.create(dto);
    }

    private void validate(MovementDTO dto){
        if (dto.value() == null || dto.value().compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidValueException("Movement value cannot be zero or null");
        }

        MovementDTO lastMovementByAccount = getFinalBalanceByAccount(dto.accountId()).get();

        if((dto.value().compareTo(BigDecimal.ZERO) < 0) && ( (lastMovementByAccount.finalBalance().compareTo(BigDecimal.ZERO) == 0)  || (
                dto.value().abs().compareTo(lastMovementByAccount.finalBalance()) > 0 ) ) ){
                throw new InvalidValueException("Valor solicitado es mayor que el saldo disponible :/");
        }
    }

    private Optional<MovementDTO> getFinalBalanceByAccount(UUID accountID){
        return queryRepository.findAllByAccountId(accountID).stream()
                .sorted((o1, o2) -> o2.date().compareTo(o1.date())).toList().stream().findFirst();
    }
}
