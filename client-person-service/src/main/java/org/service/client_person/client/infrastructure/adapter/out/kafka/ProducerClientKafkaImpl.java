package org.service.client_person.client.infrastructure.adapter.out.kafka;

import lombok.extern.log4j.Log4j2;
import org.service.client_person.client.domain.model.CustomEvent;
import org.service.client_person.client.domain.port.out.messages.IProducerClientMQ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Component
@Log4j2
public class ProducerClientKafkaImpl implements IProducerClientMQ {
    private final ReactiveKafkaProducerTemplate<String, CustomEvent<?>> template;

    @Value("${spring.kafka.producer.properties.client-external-information-topic}")
    private String topic;

    @Value("${spring.kafka.producer.properties.client-external-information-key}")
    private String key;

    public ProducerClientKafkaImpl(ReactiveKafkaProducerTemplate<String, CustomEvent<?>> template) {
        this.template = template;
    }

    @Override
    public void sendByMQ(CustomEvent<?> event) {
        template.send(topic, key, event)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(senderResult -> log.info("***** Sent {} offset : {}",
                        event.toString(), senderResult.recordMetadata().offset()))
                .doOnError(err -> log.error("##### Error sending message to topic : {}, error: {}",
                        topic, err.getMessage()))
                .retry(5)
                .subscribe();
    }
}
