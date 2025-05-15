package org.service.client_person.client.infrastructure.adapter.out.postgres;

import org.service.client_person.client.infrastructure.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IClientJPAPostgresRepository extends JpaRepository <ClientEntity, UUID> {
}
