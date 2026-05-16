package org.boiar.walletmanagement.notifications.repository.Jpa;

import org.boiar.walletmanagement.notifications.entity.Notification;
import org.boiar.walletmanagement.notifications.enums.NotificationStatus;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface NotificationRepositoryJpa extends JpaRepository<Notification, Long> {
    // All notifications for a user — paginated
    Page<Notification> findByUserIdOrderByTimingDataCreatedDateDesc(
            UUID userId, Pageable pageable
    );

    // Unread REALTIME notifications
    List<Notification> findByUserIdAndTypeAndStatus(
            UUID userId, NotificationType type, NotificationStatus status
    );

    // Count unread
    long countByUserIdAndStatus(UUID userId, NotificationStatus status);

    // Mark all read for user
    @Modifying
    @Query("""
        UPDATE Notification n
        SET n.status = 'READ', n.readAt = CURRENT_TIMESTAMP
        WHERE n.user.id = :userId
        AND n.type = 'REALTIME'
        AND n.status != 'READ'
    """)
    int markAllReadByUserId(@Param("userId") UUID userId);
}
