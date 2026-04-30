```
1. create Notification Module 

modules/notifications/
├── config/
│   └── RabbitMQConfig.java
├── enums/
│   ├── NotificationType.java        ← EMAIL, SMS, PUSH
│   └── NotificationEvent.java       ← OTP_REGISTER, OTP_FORGET_PASSWORD ...
├── strategy/
│   ├── NotificationStrategy.java    ← interface
│   ├── EmailNotificationStrategy.java
│   ├── SmsNotificationStrategy.java  ← stub for future
│   └── NotificationStrategyFactory.java
├── publisher/
│   └── NotificationPublisher.java   ← sends to RabbitMQ
├── consumer/
│   └── NotificationConsumer.java    ← listens from RabbitMQ
├── dto/
│   └── NotificationMessage.java     ← payload on the queue
├── template/
│   └── MailTemplateBuilder.java     ← builds HTML email
└── service/
    ├── NotificationService.java
    └── impl/
        └── NotificationServiceImpl.java
```