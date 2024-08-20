package org.service.client_person.client.infrastructure.adapter.out;

import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.IClientRepository;
import org.service.client_person.client.infrastructure.mapper.IClientMapper;
import org.springframework.stereotype.Repository;

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


    @Override
    public ClientDTO create(ClientDTO clientDTO) {
        return mapper.entityToDTO(repository.save(mapper.dtoToEntity(clientDTO)));
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {
        return create(clientDTO);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<ClientDTO> findAll() {
        return mapper.entityListToDTOList(repository.findAll());
    }

    @Override
    public Optional<ClientDTO> findById(UUID id) {
        return Optional.ofNullable(mapper.entityToDTO(repository.findById(id).orElse(null)));
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}