package org.boiar.walletmanagement.notifications.consumer;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boiar.walletmanagement.core.config.RabbitMQConfig;
import org.boiar.walletmanagement.notifications.dto.NotificationMessage;
import org.boiar.walletmanagement.notifications.service.NotificationPersistenceService;
import org.boiar.walletmanagement.notifications.template.MailTemplateBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final JavaMailSender mailSender;
    private final MailTemplateBuilder templateBuilder;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationPersistenceService persistenceService;

    // email
    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consumeEmail(NotificationMessage message) throws Exception {

        Locale locale = Locale.forLanguageTag(
                message.getLang() != null ? message.getLang() : "en"
        );
        LocaleContextHolder.setLocale(locale);

        try {
            if (persistenceService.isAlreadySent(message.getNotificationId())) {
                log.info("Email already sent or processed, skipping id={}", message.getNotificationId());
                return;
            }
            sendEmail(message);
            persistenceService.markSent(message.getNotificationId());
        } catch (Exception e) {
            log.error("Email failed id={}: {}", message.getNotificationId(), e.getMessage());
            throw e;
        } finally {
            LocaleContextHolder.resetLocaleContext();
        }
    }

    // Realtime
    @RabbitListener(queues = RabbitMQConfig.REALTIME_QUEUE)
    public void consumeRealtime(NotificationMessage message) {
        try {
            messagingTemplate.convertAndSendToUser(
                    message.getTo(),
                    "/queue/notifications",
                    message
            );
            persistenceService.markSent(message.getNotificationId());
        } catch (Exception e) {
            log.error("Realtime failed id={}: {}", message.getNotificationId(), e.getMessage());
            throw e;
        }
    }

    // Dead Letter
    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void consumeDeadLetter(NotificationMessage message) {
        log.error("DEAD LETTER id={} to={} event={}",
                message.getNotificationId(), message.getTo(), message.getEvent());

        persistenceService.markFailed(
                message.getNotificationId(),
                "Exceeded retry attempts — moved to DLQ"
        );
    }

    // private
    private void sendEmail(NotificationMessage message) throws Exception{
        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");

        helper.setTo(message.getTo());
        helper.setSubject(message.getTitle());
        helper.setText(
                templateBuilder.build(
                        message.getEvent().getTemplateKey(),
                        message.getVariables()
                ),
                true
        );

        mailSender.send(mime);
    }


}