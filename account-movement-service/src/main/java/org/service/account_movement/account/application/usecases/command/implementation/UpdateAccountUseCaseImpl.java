package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.IUpdateAccountUseCase;
import org.service.account_movement.account.application.usecases.exception.RecursoNotFoundException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;

import java.util.UUID;

public class UpdateAccountUseCaseImpl implements IUpdateAccountUseCase {
    private final IAccountCommandRepository commandRepository;
    private final IAccountQueryRepository queryRepository;

    public UpdateAccountUseCaseImpl(IAccountCommandRepository commandRepository,
                                    IAccountQueryRepository queryRepository) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    public AccountDTO updateById(UUID id, AccountDTO requestDTO) {
        return queryRepository.findById(id)
                .map(savedDTO -> commandRepository.update(new AccountDTO(savedDTO.id(),
                        savedDTO.clientID(),
                        savedDTO.number(),
                        savedDTO.type(),
                        savedDTO.initialBalance(),
                        requestDTO.state(), savedDTO.movements()))
                )
                .orElseThrow(() -> new RecursoNotFoundException(new StringBuilder("Account not found with id: ")
                        .append(id).toString()));
    }
}