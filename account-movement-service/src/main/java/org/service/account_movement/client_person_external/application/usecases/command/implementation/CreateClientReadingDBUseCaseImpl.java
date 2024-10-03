package org.service.account_movement.client_person_external.application.usecases.command.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;
import org.service.account_movement.client_person_external.application.usecases.command.contract.ICreateClientReadingDBUseCase;
import org.service.account_movement.client_person_external.domain.model.ClientDTO;
import org.service.account_movement.client_person_external.domain.model.ClientPersonReceiverMQDTO;
import org.service.account_movement.client_person_external.domain.model.CustomEvent;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;

import java.util.Optional;

public class CreateClientReadingDBUseCaseImpl implements ICreateClientReadingDBUseCase {
    private final IClientReadingDBRepository repository;
    private final IAccountQueryRepository accountQueryRepository;
    private final ObjectMapper objectMapper;

    public CreateClientReadingDBUseCaseImpl(IClientReadingDBRepository repository, ObjectMapper objectMapper,
                                            IAccountQueryRepository accountQueryRepository) {
        this.repository = repository;
        this.accountQueryRepository = accountQueryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void create(CustomEvent<?> event) {
        Optional.of(objectMapper.convertValue(event.data(), ClientPersonReceiverMQDTO.class))
                .ifPresent(clientPerson ->
                    repository.save(new ClientDTO(clientPerson.clientID(), clientPerson.clientState(),
                            clientPerson.person().personName(), clientPerson.person().personIdentification(),
                            accountQueryRepository.findAllByClientID(clientPerson.clientID())))
                );
    }
}