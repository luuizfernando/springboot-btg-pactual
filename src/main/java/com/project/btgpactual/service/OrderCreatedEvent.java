package com.project.btgpactual.service;


import java.util.List;

public record OrderCreatedEvent(
    Long orderId,
    Long customerId,
    List<OrderItemEvent> items
) {}