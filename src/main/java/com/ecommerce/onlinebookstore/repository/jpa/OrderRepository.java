package com.ecommerce.onlinebookstore.repository.jpa;

import com.ecommerce.onlinebookstore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderItem, UUID> {
}
