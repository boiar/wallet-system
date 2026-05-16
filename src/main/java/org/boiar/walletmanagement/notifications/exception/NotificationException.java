package org.boiar.walletmanagement.notifications.exception;

import org.boiar.walletmanagement.core.exception.BaseException;

public class NotificationException extends BaseException {

    public NotificationException(NotificationErrorCode errorCode) {
        super(errorCode);
    }

    public NotificationException(NotificationErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }
}
