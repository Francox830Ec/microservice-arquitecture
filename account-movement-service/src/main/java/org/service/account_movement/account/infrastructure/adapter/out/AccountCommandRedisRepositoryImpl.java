package org.service.account_movement.account.infrastructure.adapter.out;

import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.model.ClientDTO;
import org.service.account_movement.account.domain.port.out.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.infrastructure.mapper.IAccountMapper;
import org.service.account_movement.client_person_external.domain.port.out.IClientReadingDBRepository;
import org.service.account_movement.movement.domain.MovementDTO;
import org.service.account_movement.movement.domain.out.IMovementQueryRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class AccountCommandRedisRepositoryImpl implements IAccountCommandReadingDBRepository {
    private final IAccountRedisRepository repository;
    private final IAccountMapper mapper;
    private final IClientReadingDBRepository clientReadingDBRepository;
    private final IMovementQueryRepository movementQueryRepository;

    public AccountCommandRedisRepositoryImpl(IAccountRedisRepository repository,
                                             IAccountMapper mapper,
                                             IClientReadingDBRepository clientReadingDBRepository,
                                             IMovementQueryRepository movementQueryRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.clientReadingDBRepository = clientReadingDBRepository;
        this.movementQueryRepository = movementQueryRepository;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        return mapper.hashToDTO(repository.save(mapper.dtoToHash(accountDTO)));
    }

    @Override
    public AccountDTO create(Map<String, Object> payload) {
        List<MovementDTO> movementsByAccountList = movementQueryRepository.findAllByAccountId(UUID.fromString(payload.get("accId").toString()));
        AccountDTO accountDTO = new AccountDTO(
                UUID.fromString(payload.get("accId").toString()),
                UUID.fromString(payload.get("cliId").toString()),
                payload.get("accNumber").toString(),
                payload.get("accType").toString(),
                (BigDecimal) payload.get("accInitialBalance"),
                payload.get("accState").toString(),
                movementsByAccountList
        );
        AccountDTO accountSavedDTO = mapper.hashToDTO(repository.save(mapper.dtoToHash(accountDTO)));

        List<AccountDTO> accountsSavedByClientList = mapper.iterableToList(
                repository.findAllByClientID(UUID.fromString(payload.get("cliId").toString())));
        ClientDTO clientSavedDTO = clientReadingDBRepository.findById(UUID.fromString(payload.get("cliId").toString())).get();
        clientReadingDBRepository.save(new ClientDTO(clientSavedDTO.clientID(), clientSavedDTO.clientState(),
                clientSavedDTO.personName(), clientSavedDTO.personIdentification(), accountsSavedByClientList));

        return accountSavedDTO;
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
