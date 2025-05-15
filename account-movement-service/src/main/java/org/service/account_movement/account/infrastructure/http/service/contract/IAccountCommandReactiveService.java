package org.service.account_movement.account.infrastructure.http.service.contract;

import jakarta.validation.Valid;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Validated
public interface IAccountCommandReactiveService {
    Mono<AccountDTO> create(@Valid AccountDTO accountDTO);
    Mono<AccountDTO> update(UUID id, @Valid AccountDTO accountDTO);
    Mono<Void> delete(UUID id);
}
