package org.boiar.walletmanagement.user.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import org.boiar.walletmanagement.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
  EMAIL_ALREADY_EXISTS("ERR_EMAIL_EXISTS", "user.email.exists", CONFLICT),
  PHONE_ALREADY_EXISTS("ERR_PHONE_EXISTS", "user.phone.exists", CONFLICT),
  PASSWORD_MISMATCH("error.password.mismatch", "user.password.mismatch", BAD_REQUEST),
  USER_NOT_FOUND("ERR_USER_NOT_FOUND", "user.not.found", NOT_FOUND),
  ACCOUNT_ALREADY_DEACTIVATED(
      "ERR_ACCOUNT_ALREADY_DEACTIVATED", "user.already.deactivated", BAD_REQUEST),
  ACCOUNT_ALREADY_ACTIVATED("ERR_ACCOUNT_ALREADY_ACTIVATED", "user.already.activated", BAD_REQUEST),
  INVALID_CURRENT_PASSWORD(
      "ERR_INVALID_CURRENT_PASSWORD", "user.invalid.current.password", BAD_REQUEST),
  CHANGE_PASSWORD_MISMATCH(
      "ERR_CHANGE_PASSWORD_MISMATCH", "user.change.password.mismatch", BAD_REQUEST);

  private final String code;
  private final String messageKey;
  private final HttpStatus httpStatus;

  UserErrorCode(String code, String messageKey, HttpStatus httpStatus) {
    this.code = code;
    this.messageKey = messageKey;
    this.httpStatus = httpStatus;
  }
}
