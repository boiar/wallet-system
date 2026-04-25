package org.boiar.walletmanagement.core.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldError {
    private String field;    // "email"
    private String message;  // translated error

}
