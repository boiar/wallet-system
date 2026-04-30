package org.boiar.walletmanagement.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;

public record ResendOtpRequest(
    @NotBlank(message = "error.field.required") @Email(message = "error.field.invalid.email")
        String email,
    @NotNull(message = "error.field.required") OtpTypeEnum type) {}
