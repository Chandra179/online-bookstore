package com.ecommerce.onlinebookstore.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String username;
    private String password;

}
