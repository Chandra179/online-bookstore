package com.ecommerce.onlinebookstore.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserAccount userAccount;

    @OneToOne
    private CustomerOrder customerOrder;

    private Double amount;
    private Date paymentDate;
    private String paymentMethod;
}