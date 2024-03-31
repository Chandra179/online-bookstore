package com.ecommerce.onlinebookstore.repository;

import com.ecommerce.onlinebookstore.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}