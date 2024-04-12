package com.ecommerce.onlinebookstore.repository.jpa;

import com.ecommerce.onlinebookstore.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAccount, UUID> {
}
