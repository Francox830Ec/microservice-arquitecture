package org.service.account_movement.account.infrastructure.http.service.contract;

import org.service.account_movement.account.domain.model.AccountDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IAccountCommandReactiveService {
    Mono<AccountDTO> create(AccountDTO accountDTO);
    Mono<AccountDTO> update(UUID id, AccountDTO accountDTO);
    Mono<Void> delete(UUID id);
}
