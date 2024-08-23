package org.service.client_person.person.infrastructure.adapter.out.postgres;

import org.service.client_person.person.infrastructure.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPersonJPAPostgresRepository extends JpaRepository <PersonEntity, UUID> {
    Optional<PersonEntity> findByIdentification(String identification);
}
