package org.service.account_movement.movement.infrastructure.adapter.out.postgres;

import org.service.account_movement.movement.domain.model.MovementDTO;
import org.service.account_movement.movement.domain.out.repository.IMovementCommandRepository;
import org.service.account_movement.movement.infrastructure.mapper.IMovementMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MovementCommandPostgresRepositoryImpl implements IMovementCommandRepository {
    private final IMovementPostgresRepository repository;
    private final IMovementMapper mapper;

    public MovementCommandPostgresRepositoryImpl(IMovementPostgresRepository repository, IMovementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public MovementDTO create(MovementDTO dto) {
        return mapper.entityToDTO(repository.save(mapper.dtoToEntity(dto)));
    }

    @Transactional
    @Override
    public MovementDTO update(MovementDTO dto) {
        return create(dto);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
