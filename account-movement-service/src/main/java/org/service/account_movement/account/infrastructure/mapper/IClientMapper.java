package org.service.account_movement.account.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.service.account_movement.account.domain.model.ClientDTO;
import org.service.account_movement.account.infrastructure.hash.ClientHash;

@Mapper(componentModel = "spring")
public interface IClientMapper {
   ClientHash dtoToHash(ClientDTO dto);
   ClientDTO hashToDTO(ClientHash clientHash);
}