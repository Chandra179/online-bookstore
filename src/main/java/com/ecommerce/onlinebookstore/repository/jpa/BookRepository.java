package com.ecommerce.onlinebookstore.repository.jpa;

import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.repository.projection.BookView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
  @Query("SELECT b.title AS title, b.price AS price, b.description AS description, " +
    "(SELECT a.name FROM b.authors a) AS authors, " +
    "(SELECT g.name FROM b.genres g) AS genres " +
    "FROM Book b WHERE b.id = :id")
  BookView findBookById(UUID id);
}
