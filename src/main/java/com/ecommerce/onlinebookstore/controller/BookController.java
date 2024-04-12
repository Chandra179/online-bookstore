package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.dto.BookCreationRequest;
import com.ecommerce.onlinebookstore.entity.elasticsearch.BookEcs;
import com.ecommerce.onlinebookstore.repository.projection.BookView;
import com.ecommerce.onlinebookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping
  public void createBook(@RequestBody BookCreationRequest bookDTO) {
    bookService.createBook(bookDTO);
  }

  @GetMapping
  public Page<BookEcs> searchBooks(@RequestParam String s) {
    return bookService.search(s, Pageable.ofSize(10));
  }

  @GetMapping("/{g}")
  public Page<BookEcs> searchBooks(@PathVariable String g, @RequestParam String s) {
    return bookService.search(s, g, Pageable.ofSize(10));
  }

  @GetMapping("/{id}")
  public BookView getBook(@PathVariable UUID id) {
    return bookService.getBook(id);
  }
}
