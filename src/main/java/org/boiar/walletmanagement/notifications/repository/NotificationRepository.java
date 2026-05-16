package org.boiar.walletmanagement.notifications.repository;

import org.boiar.walletmanagement.notifications.entity.Notification;
import org.boiar.walletmanagement.notifications.enums.NotificationStatus;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {

    Page<Notification> findByUserIdOrderByTimingDataCreatedDateDesc(UUID userId, Pageable pageable);

    List<Notification> findByUserIdAndTypeAndStatus(
            UUID userId, NotificationType type, NotificationStatus status
    );

    long countByUserIdAndStatus(UUID userId, NotificationStatus status);

    int markAllReadByUserId(UUID userId);

    Optional<Notification> findById(Long notificationId);

    Notification save(Notification n);
}
