package com.project.btgpactual.domain.orders.dto;

import java.math.BigDecimal;

import com.project.btgpactual.domain.orders.Order;

public record OrderResponse(
    
    Long orderId,
    Long customerId,
    BigDecimal total

)

{
    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(order.getOrderId(), order.getCustomerId(), order.getTotal());
    }
}