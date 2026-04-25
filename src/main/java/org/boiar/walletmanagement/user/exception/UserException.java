package org.boiar.walletmanagement.user.exception;

import org.boiar.walletmanagement.core.exception.BaseException;

public class UserException extends BaseException {
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(UserErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public UserException(UserErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
