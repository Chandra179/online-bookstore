package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.dto.BookDto;
import com.ecommerce.onlinebookstore.entity.elasticsearch.BookEcs;
import com.ecommerce.onlinebookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping
  public void addBook(@RequestBody BookDto bookDTO) {
    bookService.addBook(bookDTO);
  }

  @GetMapping
  public Page<BookEcs> searchBooks(@RequestParam String s) {
    return bookService.search(s, Pageable.ofSize(10));
  }
}
