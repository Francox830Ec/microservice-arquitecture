package org.service.account_movement.movement.infrastructure.adapter.out.redis;

import org.service.account_movement.movement.domain.model.MovementDTO;
import org.service.account_movement.movement.domain.out.repository.IMovementCommandReadingDBRepository;
import org.service.account_movement.movement.infrastructure.mapper.IMovementMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Repository
public class MovementCommandRedisRepositoryImpl implements IMovementCommandReadingDBRepository {
    private final IMovementRedisRepository repository;
    private final IMovementMapper mapper;

    public MovementCommandRedisRepositoryImpl(IMovementRedisRepository repository, IMovementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MovementDTO create(MovementDTO dto) {
        return mapper.hashToDTO(repository.save(mapper.dtoToHash(dto)));
    }

    @Override
    public MovementDTO create(Map<String, Object> payload) {
        return create(new MovementDTO(UUID.fromString(payload.get("movId").toString()),
                UUID.fromString(payload.get("accId").toString()),
                LocalDateTime.ofInstant(((Date) payload.get("movDate")).toInstant(), ZoneId.of("America/Guayaquil")),
                payload.get("movType").toString(),
                (BigDecimal) payload.get("movInitialBalance"),
                (BigDecimal) payload.get("movValue"),
                (BigDecimal) payload.get("movFinalBalance")
        ));
    }
}
