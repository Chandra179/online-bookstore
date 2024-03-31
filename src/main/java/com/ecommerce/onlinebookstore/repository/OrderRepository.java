package com.ecommerce.onlinebookstore.repository;

import com.ecommerce.onlinebookstore.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}