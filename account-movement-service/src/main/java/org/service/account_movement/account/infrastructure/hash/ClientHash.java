package org.service.account_movement.account.infrastructure.hash;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Set;
import java.util.UUID;

@RedisHash("Client")
@TypeAlias("ClientHash")
public record ClientHash(
        @Id
        @Indexed
        UUID clientID,
        Boolean clientState,

        @Indexed
        String personName,

        @Indexed
        String personIdentification,

        @Reference
        Set<AccountHash> accounts
){
}
