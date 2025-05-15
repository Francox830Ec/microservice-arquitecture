package org.service.client_person.client.application.usecases.implementation;

import org.service.client_person.client.application.usecases.contract.ISendClientMQUseCase;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.model.ClientPersonProducerMQDTO;
import org.service.client_person.client.domain.model.CustomEvent;
import org.service.client_person.client.domain.model.PersonMQDTO;
import org.service.client_person.client.domain.port.out.messages.IProducerClientMQ;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.sql.Timestamp.valueOf;

public class SendClientMQUseCaseImpl implements ISendClientMQUseCase {
    private final IProducerClientMQ producerMQ;

    public SendClientMQUseCaseImpl(IProducerClientMQ producerMQ) {
        this.producerMQ = producerMQ;
    }

    @Override
    public void sendPayloadMQ(ClientDTO clientDTO, String operation) {
        sendPayloadMQ(new ClientPersonProducerMQDTO(clientDTO.id(), clientDTO.state(),
                new PersonMQDTO(clientDTO.person().id(), clientDTO.person().name(), clientDTO.person().identification())
        ), operation);
    }

    private void sendPayloadMQ(Object payload, String operation) {
        producerMQ.sendByMQ(new CustomEvent<Object>(
                UUID.randomUUID().toString(),
                valueOf(LocalDateTime.now()),
                operation,
                payload));
    }
}
