package org.boiar.walletmanagement.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.boiar.walletmanagement.core.validation.password.PasswordMatch;
import org.boiar.walletmanagement.core.validation.password.StrongPassword;

@PasswordMatch
public record RegisterRequest(
    @NotBlank(message = "error.field.required") String f_name,
    @NotBlank(message = "error.field.required") String l_name,
    @NotBlank(message = "error.field.required") @Email(message = "error.field.invalid.email")
        String email,
    @NotBlank(message = "error.field.required") String phone,
    @NotBlank(message = "error.field.required") @StrongPassword(message = "error.password.weak")
        String password,
    @NotBlank(message = "error.field.required") String confirmPassword) {}
