package org.boiar.walletmanagement.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.boiar.walletmanagement.notifications.enums.NotificationEvent;
import org.boiar.walletmanagement.notifications.enums.NotificationType;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {

    private Long notificationId;
    private String to;
    private NotificationType type;
    private NotificationEvent event;
    private String            title;
    private String            body;
    private Map<String, String> variables; // ex { "otp": "123456", "name": "Ahmed" }
    private String lang;

}
