package org.boiar.walletmanagement.notifications.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationEvent {


    // Auth
    OTP_REGISTER("otp_register", "email.subject.verify_email"),
    OTP_RESEND("resend_otp", "email.subject.resend_otp"),
    OTP_FORGET_PASSWORD("otp_forget_password", "email.subject.reset_password"),
    WELCOME("welcome", "email.subject.welcome"),

    // Wallet
    LOW_BALANCE("low_balance", "email.subject.low_balance"),
    ALERT("alert", "email.subject.alert");



    private final String templateKey;
    private final String defaultSubject;
}
