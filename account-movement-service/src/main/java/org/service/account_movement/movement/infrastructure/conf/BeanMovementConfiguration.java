package org.service.account_movement.movement.infrastructure.conf;

import org.service.account_movement.account.domain.port.out.IAccountCommandReadingDBRepository;
import org.service.account_movement.account.domain.port.out.IAccountQueryRepository;
import org.service.account_movement.client_person_external.domain.port.out.IClientReadingDBRepository;
import org.service.account_movement.movement.application.usescases.command.contract.ICreateMovementUseCase;
import org.service.account_movement.movement.application.usescases.command.contract.IReplicateMovementUseCase;
import org.service.account_movement.movement.application.usescases.command.implementation.CreateMovementUseCaseImpl;
import org.service.account_movement.movement.application.usescases.command.implementation.ReplicateMovementUseCaseImpl;
import org.service.account_movement.movement.domain.out.IMovementCommandReadingDBRepository;
import org.service.account_movement.movement.domain.out.IMovementCommandRepository;
import org.service.account_movement.movement.domain.out.IMovementQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanMovementConfiguration {
    @Bean
    ICreateMovementUseCase createMovementUseCase(final IMovementCommandRepository repository,
                                                 final IMovementQueryRepository queryRepository){
        return new CreateMovementUseCaseImpl(repository, queryRepository);
    }

    @Bean
    IReplicateMovementUseCase replicateMovementUseCase(final IMovementCommandReadingDBRepository movementCommandReadingDBRepository,
                                                       final IMovementQueryRepository movementQueryRepository,
                                                       final IMovementCommandRepository commandRepository,
                                                       final IAccountQueryRepository accountQueryRepository,
                                                       final IAccountCommandReadingDBRepository accountCommandReadingDBRepository,
                                                       final IClientReadingDBRepository clientReadingDBRepository){
        return new ReplicateMovementUseCaseImpl(movementCommandReadingDBRepository, movementQueryRepository,
                commandRepository, accountQueryRepository, accountCommandReadingDBRepository, clientReadingDBRepository);
    }
}
