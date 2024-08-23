package org.service.account_movement.account.infrastructure.adapter.out.postgres;

import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandRepository;
import org.service.account_movement.account.infrastructure.entity.AccountEntity;
import org.service.account_movement.account.infrastructure.mapper.IAccountMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class AccountCommandPostgresRepositoryImpl implements IAccountCommandRepository {
    private final IAccountPostgresRepository repository;
    private final IAccountMapper mapper;

    public AccountCommandPostgresRepositoryImpl(
            IAccountPostgresRepository repository, IAccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        AccountEntity accountEntity = mapper.dtoToEntity(accountDTO);
        AccountEntity save = repository.save(accountEntity);

        return mapper.entityToDTO(save);
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        return create(accountDTO);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}