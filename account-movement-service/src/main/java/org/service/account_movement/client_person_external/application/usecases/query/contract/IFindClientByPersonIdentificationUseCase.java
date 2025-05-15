package org.service.account_movement.client_person_external.application.usecases.query.contract;

import org.service.account_movement.client_person_external.domain.model.ClientDTO;

import java.util.Map;

public interface IFindClientByPersonIdentificationUseCase {
    ClientDTO findByPersonIdentificationAndDatesRange(String personIdentification, Map<String, String> datesRange);
}
