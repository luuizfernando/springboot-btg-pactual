package com.project.btgpactual.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.project.btgpactual.config.RabbitMQ.ORDER_CREATED_QUEUE;
import com.project.btgpactual.service.OrderCreatedEvent;
import com.project.btgpactual.service.OrderService;

@Component
public class OrderListener {

    @Autowired
    private OrderService service;

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(@Payload OrderCreatedEvent event) {
        service.save(event);
    }

}
