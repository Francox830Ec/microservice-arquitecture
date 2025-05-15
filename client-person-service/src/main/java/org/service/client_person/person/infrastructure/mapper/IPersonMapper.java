package org.service.client_person.person.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.service.client_person.person.domain.model.PersonDTO;
import org.service.client_person.person.infrastructure.entity.PersonEntity;

@Mapper(componentModel = "spring")
public interface IPersonMapper {
    PersonDTO entityToDTO(PersonEntity personEntity);
    PersonEntity dtoToEntity(PersonDTO personDTO);
}