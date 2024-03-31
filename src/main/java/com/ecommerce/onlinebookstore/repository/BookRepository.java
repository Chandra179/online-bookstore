package com.ecommerce.onlinebookstore.repository;

import com.ecommerce.onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingOrAuthors_NameContainingOrGenres_NameContaining(String title, String author, String genre);
}