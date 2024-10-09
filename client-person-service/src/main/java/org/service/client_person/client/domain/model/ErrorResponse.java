package org.service.client_person.client.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
    String message;
    Integer code;
}
