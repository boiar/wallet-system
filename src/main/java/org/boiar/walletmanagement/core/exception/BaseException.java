package org.boiar.walletmanagement.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
  private final ErrorCode errorCode;
  private final Object[] args;

  protected BaseException(ErrorCode errorCode) {
    super(errorCode.getMessageKey());
    this.errorCode = errorCode;
    this.args = null;
  }

  protected BaseException(ErrorCode errorCode, Object... args) {
    super(errorCode.getMessageKey());
    this.errorCode = errorCode;
    this.args = args;
  }

  protected BaseException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessageKey(), cause);
    this.errorCode = errorCode;
    this.args = null;
  }

  public HttpStatus getHttpStatus() {
    return errorCode.getHttpStatus();
  }

  public String getCode() {
    return errorCode.getCode();
  }
}
