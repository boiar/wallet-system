package org.boiar.walletmanagement.core.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    // exchange
    public static final String NOTIFICATION_EXCHANGE    = "notification.exchange";
    public static final String DEAD_LETTER_EXCHANGE     = "notification.dlx";

    // queue
    public static final String EMAIL_QUEUE              = "notification.email.queue";
    public static final String REALTIME_QUEUE           = "notification.realtime.queue";
    public static final String DEAD_LETTER_QUEUE        = "notification.dead.queue";

    // routing keys
    public static final String EMAIL_ROUTING_KEY        = "notification.email";
    public static final String REALTIME_ROUTING_KEY     = "notification.realtime";
    public static final String DEAD_LETTER_ROUTING_KEY  = "notification.dead";

    // exchange
    @Bean
    public TopicExchange notificationExchange() {
        return ExchangeBuilder
                .topicExchange(NOTIFICATION_EXCHANGE)
                .durable(true)
                .build();
    }

    // dead letter
    @Bean
    public DirectExchange deadLetterExchange() {
        return ExchangeBuilder
                .directExchange(DEAD_LETTER_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder
                .durable(DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_ROUTING_KEY);
    }

    // email queue
    @Bean
    public Queue emailQueue() {
        return QueueBuilder
                .durable(EMAIL_QUEUE)
                // Failed messages go to DLX
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
                .withArgument("x-message-ttl", 300_000)  // 5 min TTL
                .build();
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(notificationExchange())
                .with(EMAIL_ROUTING_KEY);
    }

    // realtime queue
    @Bean
    public Queue realtimeQueue(){
        return QueueBuilder
                .durable(REALTIME_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    @Bean
    public Binding realtimeBinding(){
        return BindingBuilder
                .bind(realtimeQueue())
                .to(notificationExchange())
                .with(REALTIME_ROUTING_KEY);
    }


    // json converter
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
