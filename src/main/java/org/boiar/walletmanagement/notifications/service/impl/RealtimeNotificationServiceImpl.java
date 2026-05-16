package org.boiar.walletmanagement.notifications.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boiar.walletmanagement.notifications.dto.NotificationMessage;
import org.boiar.walletmanagement.notifications.entity.Notification;
import org.boiar.walletmanagement.notifications.enums.NotificationEvent;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.boiar.walletmanagement.notifications.pusher.NotificationPublisher;
import org.boiar.walletmanagement.notifications.service.NotificationPersistenceService;
import org.boiar.walletmanagement.notifications.service.RealtimeNotificationService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class RealtimeNotificationServiceImpl implements RealtimeNotificationService {

    private final NotificationPublisher publisher;
    private final NotificationPersistenceService persistenceService;

    @Override
    public void sendLowBalance(UUID userId, String balance, String currency) {
        send(
                userId,
                NotificationEvent.LOW_BALANCE,
                "Low Balance Alert",
                Map.of("balance", balance, "currency", currency)
        );
    }

    @Override
    public void sendAlert(UUID userId, String msg) {
        send(
                userId,
                NotificationEvent.ALERT,
                "Alert",
                Map.of("msg", msg)
        );

    }

    // private
    private void send(UUID userId, NotificationEvent event,
                      String title, Map<String, String> variables) {

        // save in db
        Notification notification = persistenceService.savePendingByUserId(
                userId, event, NotificationType.REALTIME, title, variables
        );

        // push to rabbitMq
        publisher.publish(NotificationMessage.builder()
                .notificationId(notification.getId())
                .to(userId.toString())
                .type(NotificationType.REALTIME)
                .event(event)
                .title(title)
                .variables(variables)
                .build());
    }


}
