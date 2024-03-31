package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // methods for registration, login, profile management, etc.
}