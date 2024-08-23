package org.service.account_movement.movement.infrastructure.adapter.out;

import org.service.account_movement.movement.domain.MovementDTO;
import org.service.account_movement.movement.domain.out.IMovementCommandRepository;
import org.service.account_movement.movement.infrastructure.mapper.IMovementMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MovementCommandPostgresRepositoryImpl implements IMovementCommandRepository {
    private final IMovementPostgresRepository repository;
    private final IMovementMapper mapper;

    public MovementCommandPostgresRepositoryImpl(IMovementPostgresRepository repository, IMovementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MovementDTO create(MovementDTO dto) {
        return mapper.entityToDTO(repository.save(mapper.dtoToEntity(dto)));
    }

    @Override
    public MovementDTO update(MovementDTO dto) {
        return create(dto);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
