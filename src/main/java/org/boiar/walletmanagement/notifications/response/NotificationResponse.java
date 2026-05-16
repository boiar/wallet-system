package org.boiar.walletmanagement.notifications.response;

import lombok.Builder;
import lombok.Data;
import org.boiar.walletmanagement.notifications.enums.NotificationEvent;
import org.boiar.walletmanagement.notifications.enums.NotificationStatus;
import org.boiar.walletmanagement.notifications.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {
    private Long               id;
    private String             title;
    private String             body;
    private NotificationEvent event;
    private NotificationType type;
    private NotificationStatus status;
    private boolean            read;
    private LocalDateTime createdDate;
}
