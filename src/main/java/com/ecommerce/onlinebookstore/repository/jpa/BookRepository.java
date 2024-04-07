package com.ecommerce.onlinebookstore.repository.jpa;

import com.ecommerce.onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
