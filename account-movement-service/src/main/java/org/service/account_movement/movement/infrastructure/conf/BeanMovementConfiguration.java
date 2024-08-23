package org.service.account_movement.movement.infrastructure.conf;

import org.service.account_movement.account.domain.port.out.repository.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountCommandRepository;
import org.service.account_movement.account.domain.port.out.repository.IAccountQueryRepository;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;
import org.service.account_movement.movement.application.usescases.command.contract.ICreateMovementUseCase;
import org.service.account_movement.movement.application.usescases.command.contract.IReplicateMovementUseCase;
import org.service.account_movement.movement.application.usescases.command.implementation.CreateMovementUseCaseImpl;
import org.service.account_movement.movement.application.usescases.command.implementation.ReplicateMovementUseCaseImpl;
import org.service.account_movement.movement.domain.out.repository.IMovementCommandReadingDBRepository;
import org.service.account_movement.movement.domain.out.repository.IMovementCommandRepository;
import org.service.account_movement.movement.domain.out.repository.IMovementQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanMovementConfiguration {
    @Bean
    ICreateMovementUseCase createMovementUseCase(final IMovementCommandRepository repository,
                                                 final IMovementQueryRepository queryRepository,
                                                 final IAccountQueryRepository accountQueryRepository){
        return new CreateMovementUseCaseImpl(repository, queryRepository, accountQueryRepository);
    }

    @Bean
    IReplicateMovementUseCase replicateMovementUseCase(final IMovementCommandReadingDBRepository movementCommandReadingDBRepository,
                                                       final IMovementQueryRepository movementQueryRepository,
                                                       final IMovementCommandRepository commandRepository,
                                                       final IAccountQueryRepository accountQueryRepository,
                                                       final IAccountCommandReadingDBRepository accountCommandReadingDBRepository,
                                                       final IClientReadingDBRepository clientReadingDBRepository,
                                                       final IAccountCommandRepository accountCommandRepository){
        return new ReplicateMovementUseCaseImpl(movementCommandReadingDBRepository, movementQueryRepository,
                commandRepository, accountQueryRepository, accountCommandReadingDBRepository, clientReadingDBRepository,
                accountCommandRepository);
    }
}
