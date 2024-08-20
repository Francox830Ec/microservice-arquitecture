package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.IDeleteLogicalAccountUseCase;
import org.service.account_movement.account.application.usecases.exception.RecursoNotFoundException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.IAccountCommandRepository;
import org.service.account_movement.account.domain.port.out.IAccountQueryRepository;

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
        if(!queryRepository.existsById(id)) {
            throw new RecursoNotFoundException("Not found account with id " + id);
        }

        AccountDTO dto = queryRepository.findByIdAndState(id, "true").get();
        commandRepository.update(new AccountDTO(dto.id(), dto.clientID(), dto.number(),
                dto.type(), dto.initialBalance(), "false", dto.movements()));
    }
}
