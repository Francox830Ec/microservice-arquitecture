package org.service.account_movement.account.infrastructure.adapter.in.kafka;

import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.connect.source.SourceRecord;
import org.service.account_movement.account.application.usecases.command.contract.IReplicateAccountUseCase;
import org.service.account_movement.account.domain.port.in.messages.IAccountCDCListener;
import org.service.account_movement.account.infrastructure.shared.DebeziumEventUtil;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Log4j2
@Component
public class AccountDebeziumListenerImpl implements IAccountCDCListener {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
    private final IReplicateAccountUseCase replicateAccountUseCase;

    public AccountDebeziumListenerImpl(Configuration customerConnectorConfiguration,
                                       IReplicateAccountUseCase replicateAccountUseCase){
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(customerConnectorConfiguration
                        .edit()
                        .with("name", "account")
                        .with("topic.prefix", "accountTopic")
                        .with("slot.name", "dbz_acc_db_listener")
                        .with("table.include.list", "transaction.account")
                        .build()
                        .asProperties())
                .notifying(this::handleChangeEvent)
                .build();
        this.replicateAccountUseCase = replicateAccountUseCase;
    }

    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        Mono.fromRunnable(() -> {
            DebeziumEventUtil util = new DebeziumEventUtil(sourceRecordRecordChangeEvent);
            replicateAccountUseCase.replicateData(util.payload(), util.operation().name());
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
