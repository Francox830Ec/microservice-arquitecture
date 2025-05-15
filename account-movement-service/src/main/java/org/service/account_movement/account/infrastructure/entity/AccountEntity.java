package org.service.account_movement.account.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.service.account_movement.movement.infrastructure.entity.MovementEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account", schema = "transaction")
@Data
public class AccountEntity {
    @Id
    @Column(name = "acc_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "account")
    private List<MovementEntity> movement;

    @Column(name = "cli_id")
    private UUID clientID;

    @Column(name = "acc_number")
    private String number;

    @Column(name = "acc_type")
    private String type;

    @Column(name = "acc_initial_balance")
    private BigDecimal initialBalance;

    @Column(name = "acc_state")
    private Boolean state;
}
