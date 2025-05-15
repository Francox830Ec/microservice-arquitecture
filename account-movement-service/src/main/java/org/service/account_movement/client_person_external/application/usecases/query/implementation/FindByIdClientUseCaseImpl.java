package org.service.account_movement.client_person_external.application.usecases.query.implementation;

import org.service.account_movement.client_person_external.application.usecases.query.contract.IFindByIdClientUseCase;
import org.service.account_movement.client_person_external.domain.model.ClientDTO;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;

import java.util.Optional;
import java.util.UUID;

public class FindByIdClientUseCaseImpl implements IFindByIdClientUseCase {
    private final IClientReadingDBRepository repository;

    public FindByIdClientUseCaseImpl(IClientReadingDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ClientDTO> findByID(UUID clientID) {
        return repository.findById(clientID);
    }
}
