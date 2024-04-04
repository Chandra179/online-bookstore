package com.ecommerce.onlinebookstore.repository.jpa;

import com.ecommerce.onlinebookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}