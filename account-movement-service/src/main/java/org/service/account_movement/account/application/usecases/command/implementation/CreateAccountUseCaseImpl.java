package org.service.account_movement.account.application.usecases.command.implementation;

import org.service.account_movement.account.application.usecases.command.contract.ICreateAccountUseCase;
import org.service.account_movement.account.application.usecases.exception.InvalidValueException;
import org.service.account_movement.account.application.usecases.exception.RecursoNotFoundException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandRepository;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;

import java.math.BigDecimal;

public class CreateAccountUseCaseImpl implements ICreateAccountUseCase {
    private final IAccountCommandRepository repository;
    private final IClientReadingDBRepository clientReadingDBRepository;

    public CreateAccountUseCaseImpl(IAccountCommandRepository repository,
                                    IClientReadingDBRepository clientReadingDBRepository) {
        this.repository = repository;
        this.clientReadingDBRepository = clientReadingDBRepository;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        validateClient(accountDTO);
        validateDTO(accountDTO);
        return repository.create(buildAccountDTO(accountDTO));
    }

    private void validateDTO(AccountDTO accountDTO) {
        if(accountDTO.id() != null){
            throw new InvalidValueException("This service only allows you to create new accounts.");
        }

        if (accountDTO.initialBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidValueException("Account initialBalance must be a positive value");
        }
    }

    private AccountDTO buildAccountDTO(AccountDTO accountDTO) {
        return new AccountDTO(null, accountDTO.clientID(), getSequence(), accountDTO.type(),
                accountDTO.initialBalance(), true, null);
    }

    private void validateClient(AccountDTO accountDTO) {
        clientReadingDBRepository.findById(accountDTO.clientID()).ifPresentOrElse(clientDTO -> {
                if (Boolean.FALSE.equals(clientDTO.clientState())){
                    throw new InvalidValueException("Client is inactive.. " );
                }
            }, () -> {
                throw new RecursoNotFoundException("Client not found with id: " + accountDTO.clientID());
            }
        );
    }

    private String getSequence(){
        return String.format("%010d", repository.count() + 1);
    }
}
