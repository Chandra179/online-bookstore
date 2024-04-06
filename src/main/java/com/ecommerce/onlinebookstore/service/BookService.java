package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.dto.BookDto;
import com.ecommerce.onlinebookstore.entity.Author;
import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.repository.elasticsearch.BookElasticsearchRepository;
import com.ecommerce.onlinebookstore.repository.jpa.AuthorRepository;
import com.ecommerce.onlinebookstore.repository.jpa.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookElasticsearchRepository bookElasticsearchRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BookElasticsearchRepository bookElasticsearchRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookElasticsearchRepository = bookElasticsearchRepository;
        this.authorRepository = authorRepository;
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    public Book addBook(BookDto bookDto) {
        Author author = authorRepository.findByName(bookDto.getAuthorName());
        if (author == null) {
            author = new Author();
            author.setName(bookDto.getAuthorName());
            author = authorRepository.save(author);
        }

        // Create the book
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle(bookDto.getTitle());
        book.setAuthors(List.of(author));
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        return bookElasticsearchRepository.search(keyword, pageable);
    }
}