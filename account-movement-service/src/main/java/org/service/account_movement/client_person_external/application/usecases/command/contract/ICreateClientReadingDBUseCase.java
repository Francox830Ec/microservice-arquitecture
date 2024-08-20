package org.service.account_movement.client_person_external.application.usecases.command.contract;

import org.service.account_movement.client_person_external.domain.model.CustomEvent;

public interface ICreateClientReadingDBUseCase {
    void create(CustomEvent<?> event);
}
