package com.ecommerce.onlinebookstore.repository;

import com.ecommerce.onlinebookstore.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
}