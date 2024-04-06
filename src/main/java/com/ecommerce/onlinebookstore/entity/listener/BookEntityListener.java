package com.ecommerce.onlinebookstore.entity.listener;

import com.ecommerce.onlinebookstore.entity.Author;
import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.entity.elasticsearch.BookDetails;
import com.ecommerce.onlinebookstore.entity.Genre;
import com.ecommerce.onlinebookstore.repository.elasticsearch.BookDetailsElasticsearchRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class BookEntityListener {

    private static BookDetailsElasticsearchRepository repository;

    @Autowired
    public void setBookElasticsearchRepository(BookDetailsElasticsearchRepository bookElasticsearchRepository) {
        BookEntityListener.repository = bookElasticsearchRepository;
    }

    @PostPersist
    public void saveBook(Book book) {
      var bookDetails = BookDetails.builder()
        .bookId(book.getId())
        .bookTitle(book.getTitle())
        .price(book.getPrice())
        .authors(new ArrayList<>())
        .genres(new ArrayList<>())
        .build();

      for (Author author : book.getAuthors()) {
        bookDetails.getAuthors().add(
          BookDetails.Authors.builder()
            .id(author.getId())
            .name(author.getName())
            .build()
        );
      }
      for (Genre genre : book.getGenres()) {
        bookDetails.getGenres().add(
          BookDetails.Genre.builder()
            .name(genre.getName())
            .build()
        );
      }
      repository.save(bookDetails);
    }

    @PostRemove
    public void deleteBook(Book book) {
        repository.deleteById(book.getId());
    }
}
