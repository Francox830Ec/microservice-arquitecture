package org.service.client_person.client.application.usecases.contract;

import org.service.client_person.client.domain.model.ClientDTO;

public interface ISendClientMQUseCase {
    void sendPayloadMQ(ClientDTO clientDTO, String operation);
}
