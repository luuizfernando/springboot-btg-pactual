package com.project.btgpactual.service;

import java.math.BigDecimal;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.project.btgpactual.domain.orders.Order;
import com.project.btgpactual.domain.orders.OrderItem;
import com.project.btgpactual.domain.orders.dto.OrderCreatedEvent;
import com.project.btgpactual.domain.orders.dto.OrderResponse;
import com.project.btgpactual.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public void save(OrderCreatedEvent event) {

        var entity = new Order();

        entity.setOrderId(event.orderId());
        entity.setCustomerId(event.customerId());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));

        repository.save(entity);

    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        var orders = repository.findAllByCustomerId(customerId, pageRequest);

        return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal getTotalByCustomerId(Long customerId) {
        var aggregation = newAggregation(
            match(Criteria.where("customerId").is(customerId)),
            group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregation, "orders", Document.class);

        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
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