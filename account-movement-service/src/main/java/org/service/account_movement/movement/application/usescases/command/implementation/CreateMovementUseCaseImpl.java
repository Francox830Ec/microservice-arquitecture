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
        validateDTO(dto);
        validateAccountBalance(dto);
        return repository.create(dto);
    }

    private void validateDTO(MovementDTO dto){
        if (dto.value() == null || dto.value().compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidValueException("Movement value cannot be zero or null");
        }
    }

    private void validateAccountBalance(MovementDTO dto) {
        getFinalBalanceByAccount(dto.accountId()).ifPresent(lastMovementDTO -> {
            if((dto.value().compareTo(BigDecimal.ZERO) < 0) && ((lastMovementDTO.finalBalance().compareTo(BigDecimal.ZERO) == 0)
                    || (dto.value().abs().compareTo(lastMovementDTO.finalBalance()) > 0 ))){
                throw new InvalidValueException("Requested amount is greater than the available balance :/");
            }
        });
    }

    private Optional<MovementDTO> getFinalBalanceByAccount(UUID accountID){
        return queryRepository.findAllByAccountId(accountID).stream()
                .sorted((o1, o2) -> o2.date().compareTo(o1.date())).toList().stream().findFirst();
    }
}
