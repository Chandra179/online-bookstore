package com.ecommerce.onlinebookstore.repository;

import com.ecommerce.onlinebookstore.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}