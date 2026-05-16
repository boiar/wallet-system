package org.boiar.walletmanagement.notifications.pusher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boiar.walletmanagement.core.config.RabbitMQConfig;
import org.boiar.walletmanagement.notifications.dto.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publish(NotificationMessage message) {

        if (message.getLang() == null || message.getLang().isBlank()) {
            message.setLang(LocaleContextHolder.getLocale().getLanguage());
        }

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.NOTIFICATION_EXCHANGE,
                RabbitMQConfig.EMAIL_ROUTING_KEY,
                message
        );

        log.info("Published {} notification for event {} to {}", message.getType(), message.getEvent(), message.getTo());
    }
}
