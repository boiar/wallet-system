package org.boiar.walletmanagement.auth.requests;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank(message = "error.field.required") String refreshToken) {}
