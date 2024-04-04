package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.repository.jpa.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // methods for creating a new order, updating an order, viewing order history, etc.
}