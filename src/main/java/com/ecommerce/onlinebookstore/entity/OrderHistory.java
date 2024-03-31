package com.ecommerce.onlinebookstore.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserAccount userAccount;

    @OneToOne
    private CustomerOrder customerOrder;

    private Date orderDate;
}