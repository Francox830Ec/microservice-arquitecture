package org.service.account_movement.movement.infrastructure.conf;

import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.connect.source.SourceRecord;
import org.service.account_movement.account.infrastructure.shared.DebeziumEventUtil;
import org.service.account_movement.movement.application.usescases.command.contract.IReplicateMovementUseCase;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Log4j2
@Component
public class MovementDebeziumListenerImpl {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
    private final IReplicateMovementUseCase replicateMovementUseCase;

    @SneakyThrows
    public MovementDebeziumListenerImpl(Configuration customerConnectorConfiguration, IReplicateMovementUseCase replicateMovementUseCase) {
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(customerConnectorConfiguration
                        .edit()
                        .with("name", "movement")
                        .with("topic.prefix", "movementTopic")
                        .with("slot.name", "dbz_mov_db_listener")
                        .with("table.include.list", "transaction.movement")
                        .build()
                        .asProperties())
                .notifying(this::handleChangeEvent)
                .build();
        this.replicateMovementUseCase = replicateMovementUseCase;
    }

    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        Mono.fromRunnable(() -> {
            DebeziumEventUtil util = new DebeziumEventUtil(sourceRecordRecordChangeEvent);
            replicateMovementUseCase.replicateData(util.payload(), util.operation().name());
        })
        .subscribeOn(Schedulers.boundedElastic())
        .doOnError(e -> log.error(e.getMessage(), e))
        .subscribe();
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (Objects.nonNull(this.debeziumEngine)) {
            this.debeziumEngine.close();
        }
    }
}
