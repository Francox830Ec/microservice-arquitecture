package org.service.account_movement.client_person_external.infrastructure.adapter.in.kafka;

import lombok.extern.log4j.Log4j2;
import org.service.account_movement.client_person_external.application.usecases.command.contract.ICreateClientReadingDBUseCase;
import org.service.account_movement.client_person_external.domain.model.CustomEvent;
import org.service.account_movement.client_person_external.domain.port.in.messages.IClientConsumerMQ;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Log4j2
@Service
public class ClientConsumerKafkaImpl implements IClientConsumerMQ {
    private final ReactiveKafkaConsumerTemplate<String, CustomEvent<?>> reactiveKafkaConsumer;
    private final ICreateClientReadingDBUseCase createClientUseCase;

    public ClientConsumerKafkaImpl(
            ReactiveKafkaConsumerTemplate<String, CustomEvent<?>> reactiveKafkaConsumer,
            ICreateClientReadingDBUseCase createClientUseCase) {
        this.reactiveKafkaConsumer = reactiveKafkaConsumer;
        this.createClientUseCase = createClientUseCase;
    }

    @EventListener(ApplicationReadyEvent.class)
    private Flux<CustomEvent> consume() {
        return reactiveKafkaConsumer
                .receiveAutoAck()
                .doOnNext(consumerRecord -> log.info("***** MQ Received. Key = {}, value = {} from topic = {}, offset = {}",
                        consumerRecord.key(), consumerRecord.value(), consumerRecord.topic(), consumerRecord.offset())
                )
                .map(consumerRecord -> (CustomEvent) consumerRecord.value())
                .doOnNext(createClientUseCase::create)
                .doOnError(throwable -> {
                    log.error("##### Error while consuming MQ: {}", throwable.getMessage());
                });
    }    
}
