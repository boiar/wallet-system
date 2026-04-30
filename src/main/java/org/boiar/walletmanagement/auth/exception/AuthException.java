package org.boiar.walletmanagement.auth.exception;

import org.boiar.walletmanagement.core.exception.BaseException;

public class AuthException extends BaseException {

  public AuthException(AuthErrorCode errorCode) {
    super(errorCode);
  }

  public AuthException(AuthErrorCode errorCode, Object... args) {
    super(errorCode, args);
  }
}
