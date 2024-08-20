package org.service.client_person.client.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.service.client_person.person.infrastructure.entity.PersonEntity;

import java.util.UUID;

@Entity
@Table(name = "client", schema = "catalog")
@Data
public class ClientEntity {
    @Id
    @Column(name = "cli_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cli_password")
    private String password;

    @Column(name = "cli_state")
    private Boolean state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "per_id", referencedColumnName = "per_id")
    private PersonEntity person;
}
