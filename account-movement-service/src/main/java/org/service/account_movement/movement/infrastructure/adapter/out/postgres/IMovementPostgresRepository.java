package org.service.account_movement.movement.infrastructure.adapter.out.postgres;

import org.service.account_movement.movement.infrastructure.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovementPostgresRepository extends JpaRepository<MovementEntity, Long> {
}
