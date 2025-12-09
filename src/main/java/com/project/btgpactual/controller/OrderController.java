package com.project.btgpactual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.btgpactual.controller.dto.ApiResponse;
import com.project.btgpactual.controller.dto.PaginationResponse;
import com.project.btgpactual.domain.orders.dto.OrderResponse;
import com.project.btgpactual.service.OrderService;

@RestController
public class OrderController {
 
    @Autowired
    private OrderService service;

    @GetMapping("customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(
        @PathVariable("customerId") Long customerId,
        @RequestParam(name = "page", defaultValue = "0") Integer page,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    )
    {
        var pageResponse = service.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));

        return ResponseEntity.ok(new ApiResponse<>(
            pageResponse.getContent(),
            PaginationResponse.fromPage(pageResponse)
        ));
    }

}