package org.boiar.walletmanagement.notifications.service;

public interface EmailNotificationService {

    void sendOtpRegister(String to, String name, String otp, String lang);

    void sendOtpForgetPassword(String to, String name, String otp, String lang);

    void resendOtp(String to, String name, String otp, String lang);

    void sendWelcome(String to, String name, String lang);
}
