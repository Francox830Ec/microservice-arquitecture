package org.service.account_movement.account.infrastructure.adapter.out.redis;

import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.infrastructure.mapper.IAccountMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Repository
public class AccountCommandRedisRepositoryImpl implements IAccountCommandReadingDBRepository {
    private final IAccountRedisRepository repository;
    private final IAccountMapper mapper;

    public AccountCommandRedisRepositoryImpl(IAccountRedisRepository repository,
                                             IAccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        return mapper.hashToDTO(repository.save(mapper.dtoToHash(accountDTO)));
    }

    @Override
    public AccountDTO create(Map<String, Object> payload) {
        return create(new AccountDTO(
                UUID.fromString(payload.get("accId").toString()),
                UUID.fromString(payload.get("cliId").toString()),
                payload.get("accNumber").toString(),
                payload.get("accType").toString(),
                (BigDecimal) payload.get("accInitialBalance"),
                (Boolean) payload.get("accState"),
                null
        ));
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        return create(accountDTO);
    }

    @Override
    public AccountDTO update(Map<String, Object> map) {
        return create(map);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
