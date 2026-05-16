package org.boiar.walletmanagement.notifications.service;

import org.boiar.walletmanagement.notifications.entity.Notification;
import org.boiar.walletmanagement.notifications.enums.NotificationEvent;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.boiar.walletmanagement.notifications.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface NotificationPersistenceService {

    Notification savePending(String email, NotificationEvent event,
                             NotificationType type, String title,
                             Map<String, String> metadata);

    Notification savePendingByUserId(UUID userId, NotificationEvent event,
                                     NotificationType type, String title,
                                     Map<String, String> metadata);

    void markSent(Long notificationId);

    boolean isAlreadySent(Long notificationId);

    void markFailed(Long notificationId, String reason);

    void markRead(Long notificationId, UUID userId);

    void markAllRead(UUID userId);

    Page<NotificationResponse> getByUserId(UUID userId, Pageable pageable);

    List<NotificationResponse> getUnreadRealtime(UUID userId);

    long countUnread(UUID userId);

}
