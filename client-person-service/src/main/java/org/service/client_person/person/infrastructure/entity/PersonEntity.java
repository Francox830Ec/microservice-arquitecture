package org.service.client_person.person.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.service.client_person.client.infrastructure.entity.ClientEntity;

import java.util.UUID;

@Entity
@Table(name = "person", schema = "catalog")
@Data
public class PersonEntity {
    @Id
    @Column(name = "per_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "person")
    private ClientEntity client;

    @Column(name = "per_name")
    private String name;

    @Column(name = "per_gender")
    private String gender;

    @Column(name = "per_age")
    private Integer age;

    @Column(name = "per_identification")
    private String identification;

    @Column(name = "per_address")
    private String address;

    @Column(name = "per_phone")
    private String phone;
}