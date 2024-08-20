package org.service.account_movement.account.infrastructure.conf;

import org.service.account_movement.account.application.usecases.command.contract.*;
import org.service.account_movement.account.application.usecases.command.implementation.*;
import org.service.account_movement.account.application.usecases.query.contract.IFindAllByStateAccountUseCase;
import org.service.account_movement.account.application.usecases.query.contract.IFindByIdAndStateAccountUseCase;
import org.service.account_movement.account.application.usecases.query.implementation.FindAllByStateAccountUseCaseImpl;
import org.service.account_movement.account.application.usecases.query.implementation.FindByIdAndStateAccountUseCaseImpl;
import org.service.account_movement.account.domain.port.out.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.domain.port.out.IAccountCommandRepository;
import org.service.account_movement.account.domain.port.out.IAccountQueryRepository;
import org.service.account_movement.movement.domain.out.IMovementCommandRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanAccountConfiguration {
    @Bean
    ICreateAccountUseCase createAccountUseCase (final IAccountCommandRepository accountCommandRepository,
                                                final IMovementCommandRepository movementCommandRepository) {
        return new CreateAccountUseCaseImpl(accountCommandRepository, movementCommandRepository);
    }

    @Bean
    IUpdateAccountUseCase updateAccountUseCase (final IAccountCommandRepository accountCommandRepository,
                                                final IAccountQueryRepository accountQueryRepository) {
        return new UpdateAccountUseCaseImpl(accountCommandRepository, accountQueryRepository);
    }

    @Bean
    IDeleteLogicalAccountUseCase deleteAccountUseCase (final IAccountCommandRepository accountCommandRepository,
                                                       final IAccountQueryRepository accountQueryRepository) {
        return new DeleteLogicalAccountUseCaseImpl(accountCommandRepository, accountQueryRepository);
    }

    @Bean
    IFindAllByStateAccountUseCase findAllAccountUseCase (final IAccountQueryRepository accountQueryRepository) {
        return new FindAllByStateAccountUseCaseImpl(accountQueryRepository);
    }

    @Bean
    IFindByIdAndStateAccountUseCase findByIdAccountUseCase (final IAccountQueryRepository accountQueryRepository) {
        return new FindByIdAndStateAccountUseCaseImpl(accountQueryRepository);
    }

    @Bean
    ICreateAccountReadingDBUseCase createAccountReadingDBUseCase(final IAccountCommandReadingDBRepository repository){
        return new CreateAccountReadingDBUseCaseImpl(repository);
    }

    @Bean
    IDeleteAccountReadingDBUseCase deleteAccountReadingDBUseCase(final IAccountCommandReadingDBRepository repository){
        return new DeleteAccountReadingDBUseCaseImpl(repository);
    }

    @Bean
    IReplicateAccountUseCase replicateAccountUseCase(final IAccountCommandReadingDBRepository commandRepository,
                                                     final IAccountQueryRepository queryRepository){
        return new ReplicateAccountUseCaseImpl(commandRepository, queryRepository);
    }

}
