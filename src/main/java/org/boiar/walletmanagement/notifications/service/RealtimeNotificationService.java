package org.boiar.walletmanagement.notifications.service;

import java.util.UUID;

public interface RealtimeNotificationService {
    void sendLowBalance(UUID userId, String balance, String currency);
    void sendAlert(UUID userId, String msg);
}
