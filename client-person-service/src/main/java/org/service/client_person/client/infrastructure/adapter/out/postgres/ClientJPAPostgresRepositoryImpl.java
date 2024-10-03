package org.service.client_person.client.infrastructure.adapter.out.postgres;

import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.repository.IClientRepository;
import org.service.client_person.client.infrastructure.mapper.IClientMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClientJPAPostgresRepositoryImpl implements IClientRepository {
    private final IClientJPAPostgresRepository repository;
    private final IClientMapper mapper;

    public ClientJPAPostgresRepositoryImpl(IClientJPAPostgresRepository repository, IClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public ClientDTO create(ClientDTO clientDTO) {
        return mapper.entityToDTO(repository.save(mapper.dtoToEntity(clientDTO)));
    }

    @Transactional
    @Override
    public ClientDTO update(ClientDTO clientDTO) {
        return create(clientDTO);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional (readOnly = true)
    @Override
    public List<ClientDTO> findAll() {
        return mapper.entityListToDTOList(repository.findAll());
    }

    @Transactional (readOnly = true)
    @Override
    public Optional<ClientDTO> findById(UUID id) {
        return Optional.ofNullable(mapper.entityToDTO(repository.findById(id).orElse(null)));
    }

    @Transactional (readOnly = true)
    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}