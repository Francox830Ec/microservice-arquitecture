package org.service.account_movement.client_person_external.domain.model;

import java.sql.Timestamp;

public record CustomEvent<T>(
    String id,
    Timestamp date,
    String type,
    T data
){
}
