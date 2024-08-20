package org.service.client_person.person.infrastructure.adapter.out;

import org.service.client_person.person.domain.model.PersonDTO;
import org.service.client_person.person.domain.port.out.IPersonRepository;
import org.service.client_person.person.infrastructure.mapper.IPersonMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PersonJPAPostgresRepositoryImpl implements IPersonRepository {
    private final IPersonJPAPostgresRepository repository;
    private final IPersonMapper mapper;

    public PersonJPAPostgresRepositoryImpl(IPersonJPAPostgresRepository repository,
                                           IPersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        return mapper.entityToDTO(repository.save(mapper.dtoToEntity(personDTO)));
    }
}