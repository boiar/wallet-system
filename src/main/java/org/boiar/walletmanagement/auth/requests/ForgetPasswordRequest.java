package org.boiar.walletmanagement.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgetPasswordRequest(
    @NotBlank(message = "error.field.required") @Email(message = "error.field.invalid.email")
        String email) {}
