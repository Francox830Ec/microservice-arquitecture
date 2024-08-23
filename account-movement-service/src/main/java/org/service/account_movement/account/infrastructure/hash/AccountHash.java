package org.service.account_movement.account.infrastructure.hash;

import org.service.account_movement.movement.infrastructure.hash.MovementHash;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;


@RedisHash(value = "Account")
@TypeAlias("AccountHash")
public record AccountHash(
        @Id
        @Indexed
        UUID id,

        @Indexed
        UUID clientID,

        String number,
        String type,
        BigDecimal initialBalance,

        @Indexed
        String state,

        @Reference
        Set<MovementHash> movements
        ){
}
