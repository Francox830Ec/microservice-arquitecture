package org.service.account_movement.movement.infrastructure.hash;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RedisHash("Movement")
@TypeAlias("MovementHash")
public record MovementHash(
        @Id
        @Indexed
        UUID id,

        @Indexed
        UUID accountId,

        @Indexed
        LocalDateTime date,

        String type,
        BigDecimal initialBalance,
        BigDecimal value,
        BigDecimal finalBalance
) {
}
