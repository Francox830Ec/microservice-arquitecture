package org.service.account_movement.movement.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.service.account_movement.account.infrastructure.entity.AccountEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movement", schema = "transaction")
@Data
public class MovementEntity {
    @Id
    @Column(name = "mov_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "acc_id", insertable = true, updatable = true)
    private UUID accountId;

    @CreationTimestamp
    @Column(name = "mov_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Column(name = "mov_type")
    private String type;

    @Column(name = "mov_value")
    private BigDecimal value;

    @Column(name = "mov_final_balance")
    private BigDecimal finalBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_id", referencedColumnName = "acc_id", insertable = false, updatable = false)
    private AccountEntity account;
}