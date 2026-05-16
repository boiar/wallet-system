package org.boiar.walletmanagement.auth.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
  INVALID_CREDENTIALS("ERR_INVALID_CREDENTIALS", "error.invalid.credentials", UNAUTHORIZED),
  ACCOUNT_NOT_VERIFIED("ERR_ACCOUNT_NOT_VERIFIED", "error.account.not.verified", FORBIDDEN),
  ACCOUNT_ALREADY_VERIFIED("ERR_ACCOUNT_NOT_VERIFIED", "error.account.already.verified", FORBIDDEN),
  ACCOUNT_DISABLED("ERR_ACCOUNT_DISABLED", "error.account.disabled", FORBIDDEN),
  OTP_NOT_FOUND("ERR_OTP_NOT_FOUND", "error.otp.not.found", NOT_FOUND),
  OTP_EXPIRED("ERR_OTP_EXPIRED", "error.otp.expired", BAD_REQUEST),
  OTP_ALREADY_USED("ERR_OTP_ALREADY_USED", "error.otp.already.used", BAD_REQUEST),
  OTP_INVALID("ERR_OTP_INVALID", "error.otp.invalid", BAD_REQUEST),
  REFRESH_TOKEN_NOT_FOUND(
      "ERR_REFRESH_TOKEN_NOT_FOUND", "error.refresh.token.not.found", NOT_FOUND),
  REFRESH_TOKEN_EXPIRED("ERR_REFRESH_TOKEN_EXPIRED", "error.refresh.token.expired", UNAUTHORIZED),
  REFRESH_TOKEN_REVOKED("ERR_REFRESH_TOKEN_REVOKED", "error.refresh.token.revoked", UNAUTHORIZED),
  TOKEN_INVALID("ERR_TOKEN_INVALID", "error.token.invalid", UNAUTHORIZED),
  EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "error.email.exists", BAD_REQUEST);

  private final String code;
  private final String messageKey;
  private final HttpStatus httpStatus;
}
