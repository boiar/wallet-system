package org.boiar.walletmanagement.notifications.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boiar.walletmanagement.notifications.entity.Notification;
import org.boiar.walletmanagement.notifications.enums.NotificationEvent;
import org.boiar.walletmanagement.notifications.enums.NotificationStatus;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.boiar.walletmanagement.notifications.exception.NotificationErrorCode;
import org.boiar.walletmanagement.notifications.exception.NotificationException;
import org.boiar.walletmanagement.notifications.mapper.NotificationMapper;
import org.boiar.walletmanagement.notifications.repository.NotificationRepository;
import org.boiar.walletmanagement.notifications.response.NotificationResponse;
import org.boiar.walletmanagement.notifications.service.NotificationPersistenceService;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.exception.UserErrorCode;
import org.boiar.walletmanagement.user.exception.UserException;
import org.boiar.walletmanagement.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationPersistenceServiceImpl implements NotificationPersistenceService {

    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public Notification savePending(String email, NotificationEvent event,
                                    NotificationType type, String title,
                                    Map<String, String> metadata) {
        User user = userRepo.findByEmail(email).orElseThrow( () -> new UserException(UserErrorCode.USER_NOT_FOUND));

        return save(user, event, type, title, metadata);
    }



    @Override
    @Transactional
    public Notification savePendingByUserId(UUID userId, NotificationEvent event, NotificationType type, String title, Map<String, String> metadata) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        return save(user, event, type, title, metadata);
    }

    @Override
    @Transactional
    public void markSent(Long notificationId) {
        notificationRepo.findById(notificationId)
                .ifPresent(n -> {
                    n.markSent();
                    notificationRepo.save(n);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAlreadySent(Long notificationId) {
        return notificationRepo.findById(notificationId)
                .map(n -> n.getStatus() == NotificationStatus.SENT || n.getStatus() == NotificationStatus.READ)
                .orElse(false);
    }

    @Override
    @Transactional
    public void markFailed(Long notificationId, String reason) {
        notificationRepo.findById(notificationId)
                .ifPresent(n -> {
                    n.markFailed(reason);
                    notificationRepo.save(n);
                });
    }


    @Override
    @Transactional
    public void markRead(Long notificationId, UUID userId) {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new NotificationException(
                        NotificationErrorCode.NOTIFICATION_NOT_FOUND
                ));

        if (!notification.getUser().getId().equals(userId))
            throw new NotificationException(NotificationErrorCode.NOTIFICATION_NOT_OWNED);

        notification.markRead();
        notificationRepo.save(notification);
    }

    @Override
    @Transactional
    public void markAllRead(UUID userId) {
        notificationRepo.markAllReadByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> getByUserId(UUID userId, Pageable pageable) {
        return notificationRepo
                .findByUserIdOrderByTimingDataCreatedDateDesc(userId, pageable)
                .map(notificationMapper::toNotificationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getUnreadRealtime(UUID userId) {
        return notificationRepo
                .findByUserIdAndTypeAndStatus(
                        userId,
                        NotificationType.REALTIME,
                        NotificationStatus.PENDING
                )
                .stream()
                .map(notificationMapper::toNotificationResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnread(UUID userId) {
        return notificationRepo.countByUserIdAndStatus(userId, NotificationStatus.PENDING);
    }


    // private
    private Notification save(User user, NotificationEvent event,
                              NotificationType type, String title,
                              Map<String, String> metadata) {

        Notification notification = notificationMapper.toNotificationEntity(user, event, type, title, metadata);
        return notificationRepo.save(notification);
    }

}
