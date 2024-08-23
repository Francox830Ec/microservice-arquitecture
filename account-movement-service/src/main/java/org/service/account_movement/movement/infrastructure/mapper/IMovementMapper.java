package org.service.account_movement.movement.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.service.account_movement.movement.domain.model.MovementDTO;
import org.service.account_movement.movement.infrastructure.entity.MovementEntity;
import org.service.account_movement.movement.infrastructure.hash.MovementHash;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IMovementMapper {
    MovementDTO entityToDTO(MovementEntity entity);
    MovementEntity dtoToEntity(MovementDTO dto);
    MovementHash dtoToHash(MovementDTO dto);
    MovementDTO hashToDTO(MovementHash hash);
    List<MovementDTO> iterableToList(Iterable<MovementHash> hashes);
}
