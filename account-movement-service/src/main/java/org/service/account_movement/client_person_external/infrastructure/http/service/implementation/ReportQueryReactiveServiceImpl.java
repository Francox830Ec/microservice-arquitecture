package org.service.account_movement.client_person_external.infrastructure.http.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.service.account_movement.client_person_external.application.usecases.query.contract.IFindClientByPersonIdentificationUseCase;
import org.service.account_movement.client_person_external.domain.model.ClientDTO;
import org.service.account_movement.client_person_external.infrastructure.http.service.contract.IReportQueryReactiveService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@Log4j2
@Service
public class ReportQueryReactiveServiceImpl implements IReportQueryReactiveService {
    private final IFindClientByPersonIdentificationUseCase useCase;

    public ReportQueryReactiveServiceImpl(IFindClientByPersonIdentificationUseCase useCase) {
        this.useCase = useCase;
    }


    @Override
    public Mono<ClientDTO> reporte(String personIdentification, Map<String, String> datesRange) {
        return Mono.fromCallable(() -> useCase.findByPersonIdentificationAndDatesRange(personIdentification, datesRange))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error(e.getMessage(), e));
    }
}
