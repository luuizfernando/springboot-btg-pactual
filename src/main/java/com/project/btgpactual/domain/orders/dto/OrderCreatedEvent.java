package com.project.btgpactual.domain.orders.dto;


import java.util.List;

public record OrderCreatedEvent(
    Long orderId,
    Long customerId,
    List<OrderItemEvent> items
) {}