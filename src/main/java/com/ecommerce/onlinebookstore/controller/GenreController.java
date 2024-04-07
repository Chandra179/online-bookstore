package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.dto.BookCreationRequest;
import com.ecommerce.onlinebookstore.entity.Genre;
import com.ecommerce.onlinebookstore.repository.jpa.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenreController {

  private final GenreRepository repository;

  @Autowired
  public GenreController(GenreRepository genreRepository) {
    this.repository = genreRepository;
  }

  @GetMapping
  public List<String> getGenres() {
    return repository.findAll().stream()
      .map(Genre::getName)
      .collect(Collectors.toList());
  }

}
