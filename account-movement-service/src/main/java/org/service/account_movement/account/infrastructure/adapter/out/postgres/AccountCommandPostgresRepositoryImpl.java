package org.service.account_movement.account.infrastructure.adapter.out.postgres;

import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandRepository;
import org.service.account_movement.account.infrastructure.mapper.IAccountMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        return mapper.entityToDTO(repository.save(mapper.dtoToEntity(accountDTO)));
    }

    @Transactional
    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        return create(accountDTO);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional (readOnly = true)
    @Override
    public long count() {
        return repository.count();
    }
}