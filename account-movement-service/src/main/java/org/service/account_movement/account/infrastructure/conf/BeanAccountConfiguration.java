package org.service.account_movement.account.infrastructure.conf;

import org.service.account_movement.account.application.usecases.command.contract.*;
import org.service.account_movement.account.application.usecases.command.implementation.*;
import org.service.account_movement.account.application.usecases.query.contract.IFindAllAccountUseCase;
import org.service.account_movement.account.application.usecases.query.contract.IFindAllByStateAccountUseCase;
import org.service.account_movement.account.application.usecases.query.contract.IFindByIdAccountUseCase;
import org.service.account_movement.account.application.usecases.query.contract.IFindByIdAndStateAccountUseCase;
import org.service.account_movement.account.application.usecases.query.implementation.FindAllAccountUseCaseImpl;
import org.service.account_movement.account.application.usecases.query.implementation.FindAllByStateAccountUseCaseImpl;
import org.service.account_movement.account.application.usecases.query.implementation.FindByIdAccountUseCaseImpl;
import org.service.account_movement.account.application.usecases.query.implementation.FindByIdAndStateAccountUseCaseImpl;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanAccountConfiguration {
    @Bean
    ICreateAccountUseCase createAccountUseCase (final IAccountCommandRepository accountCommandRepository,
                                                final IClientReadingDBRepository clientReadingDBRepository) {
        return new CreateAccountUseCaseImpl(accountCommandRepository, clientReadingDBRepository);
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
    IFindAllByStateAccountUseCase findAllByStateAccountUseCase (final IAccountQueryRepository accountQueryRepository) {
        return new FindAllByStateAccountUseCaseImpl(accountQueryRepository);
    }

    @Bean
    IFindAllAccountUseCase findAllAccountUseCase (final IAccountQueryRepository accountQueryRepository) {
        return new FindAllAccountUseCaseImpl(accountQueryRepository);
    }

    @Bean
    IFindByIdAndStateAccountUseCase findByIdAndStateAccountUseCase (final IAccountQueryRepository accountQueryRepository) {
        return new FindByIdAndStateAccountUseCaseImpl(accountQueryRepository);
    }

    @Bean
    IFindByIdAccountUseCase findByIdAccountUseCase (final IAccountQueryRepository accountQueryRepository) {
        return new FindByIdAccountUseCaseImpl(accountQueryRepository);
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
