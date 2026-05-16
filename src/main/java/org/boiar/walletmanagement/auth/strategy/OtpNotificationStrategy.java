package org.boiar.walletmanagement.auth.strategy;

import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.user.entity.User;

public interface OtpNotificationStrategy {
    OtpTypeEnum getType();

    void send(User user, String code, String lang);
}
