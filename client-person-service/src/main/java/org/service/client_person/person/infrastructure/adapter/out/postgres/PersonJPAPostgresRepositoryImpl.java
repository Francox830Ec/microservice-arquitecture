package org.service.client_person.person.infrastructure.adapter.out.postgres;

import org.service.client_person.person.domain.model.PersonDTO;
import org.service.client_person.person.domain.port.out.repository.IPersonRepository;
import org.service.client_person.person.infrastructure.mapper.IPersonMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PersonJPAPostgresRepositoryImpl implements IPersonRepository {
    private final IPersonJPAPostgresRepository repository;
    private final IPersonMapper mapper;

    public PersonJPAPostgresRepositoryImpl(IPersonJPAPostgresRepository repository,
                                           IPersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public PersonDTO create(PersonDTO personDTO) {
        return mapper.entityToDTO(repository.save(mapper.dtoToEntity(personDTO)));
    }

    @Transactional (readOnly = true)
    @Override
    public Optional<PersonDTO> findById(UUID id) {
        return repository.findById(id).map(mapper::entityToDTO);
    }

    @Transactional (readOnly = true)
    @Override
    public Optional<PersonDTO> findByIdentification(String identification) {
        return repository.findByIdentification(identification).map(mapper::entityToDTO);
    }
}