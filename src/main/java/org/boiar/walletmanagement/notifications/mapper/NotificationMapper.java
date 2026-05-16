package org.boiar.walletmanagement.notifications.mapper;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.notifications.dto.NotificationMessage;
import org.boiar.walletmanagement.notifications.entity.Notification;
import org.boiar.walletmanagement.notifications.enums.NotificationEvent;
import org.boiar.walletmanagement.notifications.enums.NotificationStatus;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.boiar.walletmanagement.notifications.response.NotificationResponse;
import org.boiar.walletmanagement.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    public NotificationResponse toNotificationResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .title(n.getTitle())
                .body(n.getBody())
                .event(n.getEvent())
                .type(n.getType())
                .status(n.getStatus())
                .read(n.getStatus() == NotificationStatus.READ)
                .createdDate(n.getTimingData().getCreatedDate())
                .build();
    }

    public NotificationMessage toNotificationMessageDto(Notification n, String to, Map<String, String> variables) {
        return NotificationMessage.builder()
                .notificationId(n.getId())
                .to(to)
                .type(n.getType())
                .event(n.getEvent())
                .title(n.getTitle())
                .variables(variables)
                .build();
    }

    public Notification toNotificationEntity(User user, NotificationEvent event,
                                             NotificationType type, String title,
                                             Map<String, String> metadata) {

        return Notification.builder()
                .user(user)
                .event(event)
                .type(type)
                .title(title)
                .body("")
                .metadata(metadata)
                .status(NotificationStatus.PENDING)
                .build();
    }
}
