package org.boiar.walletmanagement.notifications.repository.impl;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.notifications.entity.Notification;
import org.boiar.walletmanagement.notifications.enums.NotificationStatus;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.boiar.walletmanagement.notifications.repository.Jpa.NotificationRepositoryJpa;
import org.boiar.walletmanagement.notifications.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationRepositoryJpa repoJpa;
    @Override
    public Page<Notification> findByUserIdOrderByTimingDataCreatedDateDesc(UUID userId, Pageable pageable) {
        return repoJpa.findByUserIdOrderByTimingDataCreatedDateDesc(userId, pageable);
    }

    @Override
    public List<Notification> findByUserIdAndTypeAndStatus(UUID userId, NotificationType type, NotificationStatus status) {
        return repoJpa.findByUserIdAndTypeAndStatus(userId, type, status);
    }

    @Override
    public long countByUserIdAndStatus(UUID userId, NotificationStatus status) {
        return repoJpa.countByUserIdAndStatus(userId, status);
    }

    @Override
    public int markAllReadByUserId(UUID userId) {
        return repoJpa.markAllReadByUserId(userId);
    }

    @Override
    public Optional<Notification> findById(Long notificationId) {
        return repoJpa.findById(notificationId);
    }

    @Override
    public Notification save(Notification n) {
        return repoJpa.save(n);
    }
}
