package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.dto.BookDto;
import com.ecommerce.onlinebookstore.entity.elasticsearch.BookDetails;
import com.ecommerce.onlinebookstore.service.BookDetailsElasticsearchService;
import com.ecommerce.onlinebookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;
  private final BookDetailsElasticsearchService bookDetailsElasticsearchService;

  @Autowired
  public BookController(BookService bookService, BookDetailsElasticsearchService bookDetailsElasticsearchService) {
    this.bookService = bookService;
    this.bookDetailsElasticsearchService = bookDetailsElasticsearchService;
  }

  @PostMapping
  public void addBook(@RequestBody BookDto bookDTO) {
    bookService.addBook(bookDTO);
  }

  @GetMapping
  public Page<BookDetails> searchBooks(@RequestParam String s) {
    return bookDetailsElasticsearchService.search(s, Pageable.ofSize(10));
  }
}
