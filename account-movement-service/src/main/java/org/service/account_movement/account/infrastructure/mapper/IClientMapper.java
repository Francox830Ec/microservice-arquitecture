package org.service.account_movement.account.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.service.account_movement.account.infrastructure.hash.ClientHash;
import org.service.account_movement.client_person_external.domain.model.ClientDTO;

@Mapper(componentModel = "spring")
public interface IClientMapper {
   ClientHash dtoToHash(ClientDTO dto);
   ClientDTO hashToDTO(ClientHash clientHash);
}