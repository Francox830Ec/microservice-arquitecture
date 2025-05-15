package org.service.account_movement.account.infrastructure.http.service.contract;

import org.service.account_movement.account.domain.model.AccountDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IAccountQueryReactiveService {
    Mono<AccountDTO> findById(UUID id);
    Flux<AccountDTO> findAll();
}
