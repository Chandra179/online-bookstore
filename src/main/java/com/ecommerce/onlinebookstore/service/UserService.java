package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.repository.jpa.UserAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserAccountRepository userRepository;

    public UserService(UserAccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    // methods for registration, login, profile management, etc.
}
