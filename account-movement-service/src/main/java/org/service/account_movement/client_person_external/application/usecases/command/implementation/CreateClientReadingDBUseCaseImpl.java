package org.service.account_movement.client_person_external.application.usecases.command.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.service.account_movement.account.domain.model.ClientDTO;
import org.service.account_movement.client_person_external.application.usecases.command.contract.ICreateClientReadingDBUseCase;
import org.service.account_movement.client_person_external.domain.model.ClientPersonReceiverMQDTO;
import org.service.account_movement.client_person_external.domain.model.CustomEvent;
import org.service.account_movement.client_person_external.domain.port.out.IClientReadingDBRepository;

import java.util.Optional;

public class CreateClientReadingDBUseCaseImpl implements ICreateClientReadingDBUseCase {
    private final IClientReadingDBRepository repository;
    private final ObjectMapper objectMapper;


    public CreateClientReadingDBUseCaseImpl(IClientReadingDBRepository repository,
                                            ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void create(CustomEvent<?> event) {
        Optional.of(objectMapper.convertValue(event.data(), ClientPersonReceiverMQDTO.class))
                .ifPresent(clientPerson ->
                    repository.save(new ClientDTO(clientPerson.clientID(), clientPerson.clientState(),
                            clientPerson.person().personName(), clientPerson.person().personIdentification(), null)));
    }
}