package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.IDeleteLogicalAccountUseCase;
import org.service.account_movement.account.application.usecases.exception.RecursoNotFoundException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;

import java.util.UUID;

public class DeleteLogicalAccountUseCaseImpl implements IDeleteLogicalAccountUseCase {
    private final IAccountCommandRepository commandRepository;
    private final IAccountQueryRepository queryRepository;

    public DeleteLogicalAccountUseCaseImpl(IAccountCommandRepository commandRepository,
                                           IAccountQueryRepository queryRepository) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    public void deleteById(UUID id) {
        queryRepository.findById(id).ifPresentOrElse(accountDTO ->
            commandRepository.update(
                    new AccountDTO(accountDTO.id(), accountDTO.clientID(), accountDTO.number(), accountDTO.type(),
                    accountDTO.initialBalance(), false, accountDTO.movements()
            )),() -> {
            throw new RecursoNotFoundException("Account not found with id " + id);
        });
    }
}
