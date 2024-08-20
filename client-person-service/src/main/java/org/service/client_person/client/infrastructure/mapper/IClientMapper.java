package org.service.client_person.client.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.infrastructure.entity.ClientEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClientMapper {
    ClientDTO entityToDTO(ClientEntity clientEntity);
    ClientEntity dtoToEntity(ClientDTO clientDTO);
    ClientDTO dtoToDTO(ClientDTO clientDTO);
    List<ClientDTO> entityListToDTOList(List<ClientEntity> clientEntityList);
}
