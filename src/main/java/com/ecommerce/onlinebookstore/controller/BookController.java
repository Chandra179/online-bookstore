package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.service.BookService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String s) {
        return bookService.searchBooks(s);
    }
}