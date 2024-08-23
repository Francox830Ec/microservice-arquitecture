package org.service.account_movement.movement.application.usescases.command.implementation;

import org.service.account_movement.account.application.usecases.exception.InvalidValueException;
import org.service.account_movement.account.application.usecases.exception.RecursoNotFoundException;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;
import org.service.account_movement.movement.application.usescases.command.contract.ICreateMovementUseCase;
import org.service.account_movement.movement.domain.model.MovementDTO;
import org.service.account_movement.movement.domain.out.repository.IMovementCommandRepository;
import org.service.account_movement.movement.domain.out.repository.IMovementQueryRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class CreateMovementUseCaseImpl implements ICreateMovementUseCase {
    private final IMovementCommandRepository repository;
    private final IMovementQueryRepository queryRepository;
    private final IAccountQueryRepository accountQueryRepository;

    public CreateMovementUseCaseImpl(IMovementCommandRepository repository,
                                     IMovementQueryRepository queryRepository, IAccountQueryRepository accountQueryRepository) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.accountQueryRepository = accountQueryRepository;
    }

    @Override
    public MovementDTO create(MovementDTO dto) {
        validateAccount(dto);
        validateDTO(dto);
        validateAccountBalance(dto);
        return repository.create(dto);
    }

    private void validateDTO(MovementDTO dto){
        if(dto.id() != null){
            throw new InvalidValueException("This service only allows you to create new movements.");
        }

        if (dto.value().compareTo(BigDecimal.ZERO) == 0) {
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

    private void validateAccount(MovementDTO dto) {
        accountQueryRepository.findById(dto.accountId()).ifPresentOrElse(accountDTO -> {
            if (Boolean.FALSE.equals(accountDTO.state())){
                throw new InvalidValueException("Account is inactive.. " );
            }
        }, () -> {
            throw new RecursoNotFoundException("Account not found with id " + dto.accountId());
        });
    }
}
