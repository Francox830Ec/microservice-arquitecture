package org.service.account_movement.account.application.usecases.query.implementation;

import org.service.account_movement.account.application.usecases.query.contract.IFindAllByStateAccountUseCase;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;

import java.util.List;

public class FindAllByStateAccountUseCaseImpl implements IFindAllByStateAccountUseCase {
    private final IAccountQueryRepository repository;

    public FindAllByStateAccountUseCaseImpl(IAccountQueryRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<AccountDTO> findAllByState(Boolean state) {
        return repository.findAllByState(state);
    }
}
