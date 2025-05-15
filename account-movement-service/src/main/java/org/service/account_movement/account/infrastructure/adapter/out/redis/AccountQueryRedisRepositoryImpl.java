package org.service.account_movement.account.infrastructure.adapter.out.redis;

import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;
import org.service.account_movement.account.infrastructure.mapper.IAccountMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountQueryRedisRepositoryImpl implements IAccountQueryRepository {
    private final IAccountRedisRepository repository;
    private final IAccountMapper mapper;

    public AccountQueryRedisRepositoryImpl(IAccountRedisRepository repository, IAccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AccountDTO> findAll() {
        return mapper.iterableToList(repository.findAll());
    }

    @Override
    public List<AccountDTO> findAllByState(Boolean state) {
        return mapper.iterableToList(repository.findAllByState(state));
    }

    @Override
    public Optional<AccountDTO> findById(UUID id) {
        return Optional.ofNullable(mapper.hashToDTO(repository.findById(id).orElse(null)));
    }

    @Override
    public Optional<AccountDTO> findByIdAndState(UUID uuid, Boolean state) {
        return Optional.ofNullable(mapper.hashToDTO(repository.findByIdAndState(uuid, state).orElse(null)));
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public List<AccountDTO> findAllByClientID(UUID clientID) {
        return mapper.iterableToList(repository.findAllByClientID(clientID));
    }

    @Override
    public long count() {
        return repository.count();
    }
}