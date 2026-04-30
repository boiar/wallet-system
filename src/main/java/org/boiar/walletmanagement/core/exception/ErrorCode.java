package org.boiar.walletmanagement.core.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  String getCode();

  String getMessageKey();

  HttpStatus getHttpStatus();
}
