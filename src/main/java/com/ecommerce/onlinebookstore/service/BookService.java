package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.dto.BookDto;
import com.ecommerce.onlinebookstore.entity.Author;
import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.entity.Genre;
import com.ecommerce.onlinebookstore.repository.jpa.AuthorRepository;
import com.ecommerce.onlinebookstore.repository.jpa.BookRepository;
import com.ecommerce.onlinebookstore.repository.jpa.GenreRepository;
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
    private final GenreRepository genreRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    public void addBook(BookDto bookDto) {
      Author author = authorRepository.findByName(bookDto.getAuthorName());
      if (author == null) {
        author = new Author();
        author.setName(bookDto.getAuthorName());
        author = authorRepository.save(author);
      }

      // TODO: Genre should be a fixed list, so we should not create a new genre if it does not exist
      Genre genre = genreRepository.findByName(bookDto.getGenreName());
      if (genre == null) {
        genre = new Genre();
        genre.setName(bookDto.getGenreName());
        genre = genreRepository.save(genre);
      }

      // Create the book
      Book book = new Book();
      book.setId(UUID.randomUUID());
      book.setTitle(bookDto.getTitle());
      book.setAuthors(List.of(author));
      book.setGenres(List.of(genre));
      bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
