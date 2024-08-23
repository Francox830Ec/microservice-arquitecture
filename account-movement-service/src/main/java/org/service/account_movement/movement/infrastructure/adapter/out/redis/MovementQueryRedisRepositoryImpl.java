package org.service.account_movement.movement.infrastructure.adapter.out.redis;

import org.service.account_movement.movement.domain.model.MovementDTO;
import org.service.account_movement.movement.domain.out.repository.IMovementQueryRepository;
import org.service.account_movement.movement.infrastructure.mapper.IMovementMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MovementQueryRedisRepositoryImpl implements IMovementQueryRepository {
    private final IMovementRedisRepository repository;
    private final IMovementMapper mapper;

    public MovementQueryRedisRepositoryImpl(IMovementRedisRepository repository, IMovementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<MovementDTO> findAll() {
        return mapper.iterableToList(repository.findAll());
    }

    @Override
    public Optional<MovementDTO> findById(UUID id) {
        return Optional.ofNullable(mapper.hashToDTO(repository.findById(id).orElse(null)));
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public List<MovementDTO> findAllByAccountId(UUID accountId) {
        return mapper.iterableToList(repository.findAllByAccountId(accountId));
    }

    @Override
    public Optional<MovementDTO> findFirstByAccountIdAndDateOrderByDateDesc(UUID accountId, LocalDateTime date) {
        return Optional.ofNullable(mapper.hashToDTO(repository.findFirstByAccountIdAndDate(accountId, date).orElse(null)));
    }

    @Override
    public Optional<MovementDTO> findTopByDateLessThanOrderByDateDesc(LocalDateTime date) {
        return Optional.ofNullable(mapper.hashToDTO(repository.findTopByDateLessThanOrderByDateDesc(date).orElse(null)));
    }

    @Override
    public Optional<MovementDTO> findTopByAccountIdOrderByDateDesc(UUID accountId) {
        return Optional.ofNullable(mapper.hashToDTO(repository.findTopByAccountIdOrderByDateDesc(accountId).get()));
    }
}
