package org.service.client_person.client.domain.model;

import java.sql.Timestamp;

public record CustomEvent<T>(
    String id,
    Timestamp date,
    String type,
    T data
){
}
