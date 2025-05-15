package org.service.account_movement.account.infrastructure.adapter.out.postgres;

import org.service.account_movement.account.infrastructure.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAccountPostgresRepository extends JpaRepository<AccountEntity, UUID> {
}
