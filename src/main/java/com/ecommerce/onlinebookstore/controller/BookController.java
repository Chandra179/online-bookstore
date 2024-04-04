package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.dto.BookDto;
import com.ecommerce.onlinebookstore.entity.Author;
import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.repository.jpa.AuthorRepository;
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
    private AuthorRepository authorRepository;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book addBook(@RequestBody BookDto bookDTO) {
        Author author = authorRepository.findByName(bookDTO.getAuthorName());
        if (author == null) {
            author = new Author();
            author.setName(bookDTO.getAuthorName());
            author = authorRepository.save(author);
        }

        // Create the book
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle(bookDTO.getTitle());
        book.getAuthors().add(author);
        return bookService.addBook(book);
    }

    @GetMapping
    public Page<Book> searchBooks(@RequestParam String s) {
        return bookService.searchBooks(s, Pageable.ofSize(10));
    }
}