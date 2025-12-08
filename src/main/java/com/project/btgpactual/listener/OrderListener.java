package com.project.btgpactual.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.project.btgpactual.config.RabbitMQ.ORDER_CREATED_QUEUE;
import com.project.btgpactual.domain.orders.OrderCreated;

@Component
public class OrderListener {

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreated> message) {
        
    }

}