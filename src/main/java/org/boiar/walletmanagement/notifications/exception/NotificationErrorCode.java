package org.boiar.walletmanagement.notifications.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {

  NOTIFICATION_NOT_FOUND("NOTIFICATION_NOT_FOUND", "error.notification.not.found", BAD_REQUEST),
  NOTIFICATION_NOT_OWNED("NOTIFICATION_NOT_OWNED", "error.notification.not.owned", BAD_REQUEST);

  private final String code;
  private final String messageKey;
  private final HttpStatus httpStatus;
}
