package org.boiar.walletmanagement.notifications.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boiar.walletmanagement.core.lang.LocaleHelper;
import org.boiar.walletmanagement.notifications.entity.Notification;
import org.boiar.walletmanagement.notifications.enums.NotificationEvent;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.boiar.walletmanagement.notifications.mapper.NotificationMapper;
import org.boiar.walletmanagement.notifications.pusher.NotificationPublisher;
import org.boiar.walletmanagement.notifications.service.EmailNotificationService;
import org.boiar.walletmanagement.notifications.service.NotificationPersistenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final NotificationPublisher publisher;
    private final NotificationPersistenceService persistenceService;
    private final NotificationMapper notificationMapper;
    private final LocaleHelper localeHelper;

    @Value("${app.otp.expiration-minutes:5}")
    private int otpExpirationMinutes;

    @Override
    public void sendOtpRegister(String to, String name, String otp, String lang) {
        send(
                to,
                lang,
                NotificationEvent.OTP_REGISTER,
                NotificationEvent.OTP_REGISTER.getDefaultSubject(),
                Map.of(
                        "username", name,
                        "otp", otp,
                        "expiry", String.valueOf(otpExpirationMinutes))
        );

    }

    @Override
    public void sendOtpForgetPassword(String to, String name, String otp, String lang) {
        send(
                to,
                lang,
                NotificationEvent.OTP_FORGET_PASSWORD,
                localeHelper.get(NotificationEvent.OTP_FORGET_PASSWORD.getDefaultSubject()),
                Map.of(
                        "username", name,
                        "otp", otp,
                        "expiry", String.valueOf(otpExpirationMinutes)
                )
        );
    }

    @Override
    public void resendOtp(String to, String name, String otp, String lang) {
        send(
                to,
                lang,
                NotificationEvent.OTP_RESEND,
                localeHelper.get(NotificationEvent.OTP_RESEND.getDefaultSubject()),
                Map.of(
                        "username", name,
                        "otp", otp,
                        "expiry", String.valueOf(otpExpirationMinutes)
                )
        );
    }

    @Override
    public void sendWelcome(String to, String name, String lang) {
        send(
                to,
                lang,
                NotificationEvent.WELCOME,
                NotificationEvent.WELCOME.getDefaultSubject(),
                Map.of("name", name)
        );
    }


    private void send(String to, String lang,
                      NotificationEvent event,
                      String title,
                      Map<String, String> variables) {
        // save db
        Notification notification = persistenceService.savePending(to, event, NotificationType.EMAIL, title, variables);


        // push to rabbitMQ
        publisher.publish(notificationMapper.toNotificationMessageDto(notification, to, variables));
    }


}
