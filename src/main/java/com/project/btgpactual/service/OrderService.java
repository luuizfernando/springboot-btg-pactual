package com.project.btgpactual.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.btgpactual.domain.orders.Order;
import com.project.btgpactual.domain.orders.OrderItem;
import com.project.btgpactual.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    
    public void save(OrderCreatedEvent event) {

        var entity = new Order();

        entity.setOrderId(event.orderId());
        entity.setCustomerId(event.customerId());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));

        repository.save(entity);

    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.items()
                .stream()
                .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.items().stream()
                .map(i -> new OrderItem(i.product(), i.quantity(), i.price()))
                .toList();
    }

}