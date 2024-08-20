package org.service.account_movement.client_person_external.application.usecases.query.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.model.ClientDTO;
import org.service.account_movement.client_person_external.application.usecases.exception.InvalidValueException;
import org.service.account_movement.client_person_external.application.usecases.query.contract.IFindClientByPersonIdentificationUseCase;
import org.service.account_movement.client_person_external.domain.port.out.IClientReadingDBRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FindClientByPersonIdentificationUseCaseImpl implements IFindClientByPersonIdentificationUseCase {
    private final IClientReadingDBRepository repository;

    public FindClientByPersonIdentificationUseCaseImpl(IClientReadingDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientDTO findByPersonIdentificationAndDatesRange(String personIdentification, Map<String, String> datesRange) {
        ClientDTO clientDTO = validateClient(personIdentification);
        Map<String, LocalDateTime> datesRangeMap = validateDatesRange(datesRange);

        List<AccountDTO> accountsSortedlist = clientDTO.accounts()
                .stream().map(accountDTO -> new AccountDTO(accountDTO.id(), accountDTO.clientID(), accountDTO.number(),
                        accountDTO.type(), accountDTO.initialBalance(), accountDTO.state(),
                        accountDTO.movements().stream().filter(dto ->
                                        (datesRangeMap.get("initialDate").isBefore(dto.date())
                                                || datesRangeMap.get("initialDate").equals(dto.date()))
                                    && (datesRangeMap.get("finalDate").isAfter(dto.date())
                                                || datesRangeMap.get("finalDate").equals(dto.date()))
                        ).sorted((o1, o2) -> o2.date().compareTo(o1.date())).toList()
                )).sorted(Comparator.comparing(AccountDTO::number)).toList();

        return new ClientDTO(clientDTO.clientID(), clientDTO.clientState(), clientDTO.personName(),
                clientDTO.personIdentification(), accountsSortedlist);
    }

    private ClientDTO validateClient(String personIdentification) {
        Optional<ClientDTO> optionalClientDTO = Optional.ofNullable(repository.findByPersonIdentification(personIdentification)
                .orElseThrow(() -> new EntityNotFoundException("Client with identification " + personIdentification + " not found")));
        return optionalClientDTO.get();
    }

    private Map<String, LocalDateTime> validateDatesRange(Map<String, String> datesRange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long maxMonthsBetweenDates = 3;
        Map<String, LocalDateTime> datesRangeMap = new HashMap<String, LocalDateTime>();

        try{
            LocalDateTime initialDate = LocalDate.parse(datesRange.get("fechaInicial"), formatter).atStartOfDay();
            LocalDateTime finalDate = LocalDate.parse(datesRange.get("fechaFinal"), formatter).atTime(LocalTime.MAX);

            if(initialDate.isAfter(finalDate)){
                throw new InvalidValueException("Fecha inicial no puede ser mayor que la fecha final..");
            }

            int initialRange = initialDate.toLocalDate().compareTo(LocalDate.now().minusMonths(maxMonthsBetweenDates));
            int finalRange = finalDate.toLocalDate().compareTo(LocalDate.now());

            if (initialRange >= 0 && finalRange <= 0){
                datesRangeMap = Map.of("initialDate", initialDate, "finalDate", finalDate);
            }else {
                throw new InvalidValueException("Sólo se permite consultar hasta " + maxMonthsBetweenDates +
                        " meses anteriores a la fecha actual. Ejemplo: De " + LocalDate.now().minusMonths(maxMonthsBetweenDates)
                        + " a " + LocalDate.now());
            }
        }catch (Exception exception){
            throw new InvalidValueException("Error en el formato de fechas: " + exception.getMessage());
        }

        return datesRangeMap;
    }
}
