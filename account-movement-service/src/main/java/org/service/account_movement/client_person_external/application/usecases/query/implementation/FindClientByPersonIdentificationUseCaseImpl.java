package org.service.account_movement.client_person_external.application.usecases.query.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.client_person_external.application.usecases.exception.InvalidValueException;
import org.service.account_movement.client_person_external.application.usecases.query.contract.IFindClientByPersonIdentificationUseCase;
import org.service.account_movement.client_person_external.domain.model.ClientDTO;
import org.service.account_movement.client_person_external.domain.port.out.repositoy.IClientReadingDBRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class FindClientByPersonIdentificationUseCaseImpl implements IFindClientByPersonIdentificationUseCase {
    private final IClientReadingDBRepository repository;

    public FindClientByPersonIdentificationUseCaseImpl(IClientReadingDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientDTO findByPersonIdentificationAndDatesRange(String personIdentification, Map<String, String> datesRange) {
        return getClientDTO(personIdentification)
                .map(clientDTO -> {
                    Map<String, LocalDateTime> datesRangeMap = validateDatesRange(datesRange);
                    return new ClientDTO(clientDTO.clientID(), clientDTO.clientState(), clientDTO.personName(),
                            clientDTO.personIdentification(),
                            clientDTO.accounts()
                                    .stream().map(accountDTO ->
                                            new AccountDTO(accountDTO.id(), accountDTO.clientID(), accountDTO.number(),
                                            accountDTO.type(), accountDTO.initialBalance(), accountDTO.state(),
                                            accountDTO.movements().stream().filter(dto ->
                                                    (datesRangeMap.get("initialDate").isBefore(dto.date())
                                                            || datesRangeMap.get("initialDate").equals(dto.date()))
                                                            && (datesRangeMap.get("finalDate").isAfter(dto.date())
                                                            || datesRangeMap.get("finalDate").equals(dto.date()))
                                            ).sorted((o1, o2) -> o2.date().compareTo(o1.date())).toList()
                                    )).sorted(Comparator.comparing(AccountDTO::number)).toList());
                }).orElseThrow(() -> new EntityNotFoundException("Client with identification " + personIdentification + " not found"));
    }

    private Optional<ClientDTO> getClientDTO(String personIdentification) {
        return repository.findByPersonIdentification(personIdentification);
    }


    private Map<String, LocalDateTime> validateDatesRange(Map<String, String> datesRange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long maxMonthsBetweenDates = 3;
        String exampleDate = "Example: From " + LocalDate.now().minusMonths(maxMonthsBetweenDates) + " to " + LocalDate.now();
        Map<String, LocalDateTime> datesRangeMap;

        try{
            LocalDateTime initialDate = LocalDate.parse(datesRange.get("fechaInicial"), formatter).atStartOfDay();
            LocalDateTime finalDate = LocalDate.parse(datesRange.get("fechaFinal"), formatter).atTime(LocalTime.MAX);

            if(initialDate.isAfter(finalDate)){
                throw new InvalidValueException("Initial date cannot be greater than finish date..");
            }

            int initialRange = initialDate.toLocalDate().compareTo(LocalDate.now().minusMonths(maxMonthsBetweenDates));
            int finalRange = finalDate.toLocalDate().compareTo(LocalDate.now());

            if (initialRange >= 0 && finalRange <= 0){
                datesRangeMap = Map.of("initialDate", initialDate, "finalDate", finalDate);
            }else {
                throw new InvalidValueException("Only the last " + maxMonthsBetweenDates + " months to the current date "
                        + " are allowed in this report. " + exampleDate);
            }
        }catch (Exception exception){
            throw new InvalidValueException("Error in date format.. " + exampleDate);
        }

        return datesRangeMap;
    }
}
