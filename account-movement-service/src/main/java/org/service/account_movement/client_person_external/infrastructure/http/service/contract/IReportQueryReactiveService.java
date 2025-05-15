package org.service.account_movement.client_person_external.infrastructure.http.service.contract;

import org.service.account_movement.client_person_external.domain.model.ClientDTO;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface IReportQueryReactiveService {
    Mono<ClientDTO> reporte(String personIdentification, Map<String, String> datesRange);
}
