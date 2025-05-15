package org.service.account_movement.client_person_external.infrastructure.adapter.out.redis;

import org.service.account_movement.account.infrastructure.mapper.IClientMapper;
import org.service.account_movement.client_person_external.domain.model.ClientDTO;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ClientRedisRepositoryImpl implements IClientReadingDBRepository {
    private final IClientRedisRepository repository;
    private final IClientMapper mapper;

    public ClientRedisRepositoryImpl(IClientRedisRepository repository, IClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(ClientDTO dto) {
        repository.save(mapper.dtoToHash(dto));
    }

    @Override
    public Optional<ClientDTO> findById(UUID clientID) {
        return Optional.ofNullable(mapper.hashToDTO(repository.findById(clientID).orElse(null)));
    }

    @Override
    public Optional<ClientDTO> findByPersonIdentification(String personIdentification) {
        return Optional.ofNullable(mapper.hashToDTO(repository.findByPersonIdentification(personIdentification).orElse(null)));
    }
}