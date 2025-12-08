package com.project.btgpactual.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.btgpactual.domain.orders.Order;

public interface OrderRepository extends MongoRepository<Order, Long> {
    
}