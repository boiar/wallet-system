package org.boiar.walletmanagement.auth.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.auth.strategy.OtpNotificationStrategy;
import org.boiar.walletmanagement.notifications.service.EmailNotificationService;
import org.boiar.walletmanagement.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgetPasswordOtpNotificationStrategy implements OtpNotificationStrategy {
    private final EmailNotificationService emailNotificationService;

    @Override
    public OtpTypeEnum getType() {
        return OtpTypeEnum.FORGET_PASSWORD;
    }

    @Override
    public void send(User user, String code, String lang) {

        emailNotificationService.sendOtpForgetPassword(
                user.getEmail(),
                user.getFullName(),
                code,
                lang
        );
    }
}
