package org.service.client_person.client.domain.port.out.messages;

import org.service.client_person.client.domain.model.CustomEvent;

public interface IProducerClientMQ {
    void sendByMQ(CustomEvent<?> event);
}