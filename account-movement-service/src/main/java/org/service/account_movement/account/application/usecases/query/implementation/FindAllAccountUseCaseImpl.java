package org.service.account_movement.account.application.usecases.query.implementation;

import org.service.account_movement.account.application.usecases.query.contract.IFindAllAccountUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;

import java.util.Comparator;
import java.util.List;

public class FindAllAccountUseCaseImpl implements IFindAllAccountUseCase {
    private final IAccountQueryRepository repository;

    public FindAllAccountUseCaseImpl(IAccountQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AccountDTO> findAll() {
        return repository.findAll()
                .stream().map(accountDTO ->
                        new AccountDTO(accountDTO.id(), accountDTO.clientID(), accountDTO.number(),
                        accountDTO.type(), accountDTO.initialBalance(), accountDTO.state(),
                        accountDTO.movements().stream().sorted((o1, o2) -> o2.date().compareTo(o1.date())).toList())
                ).sorted(Comparator.comparing(AccountDTO::number)).toList();
    }
}
