package com.project.btgpactual.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {

    public static final String ORDER_CREATED_QUEUE = "order-created-queue";

    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public Declarable orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE);
    }
    
}