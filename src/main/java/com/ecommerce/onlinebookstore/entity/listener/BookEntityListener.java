package com.ecommerce.onlinebookstore.entity.listener;

import com.ecommerce.onlinebookstore.entity.Author;
import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.entity.elasticsearch.BookEcs;
import com.ecommerce.onlinebookstore.entity.Genre;
import com.ecommerce.onlinebookstore.repository.elasticsearch.BookEcsRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BookEntityListener {

  private final BookEcsRepository repository;

  @Autowired
  public BookEntityListener(BookEcsRepository repository) {
    this.repository = repository;
  }

  @PostPersist
  public void saveBook(Book book) {
    var bookEcs = BookEcs.builder()
      .bookId(book.getId())
      .bookTitle(book.getTitle())
      .price(book.getPrice())
      .authors(new ArrayList<>())
      .genres(new ArrayList<>())
      .build();

    for (Author author : book.getAuthors()) {
      bookEcs.getAuthors().add(
        BookEcs.Authors.builder()
          .id(author.getId().toString())
          .name(author.getName())
          .build()
      );
    }
    for (Genre genre : book.getGenres()) {
      bookEcs.getGenres().add(
        BookEcs.Genre.builder()
          .name(genre.getName())
          .build()
      );
    }
    repository.save(bookEcs);
  }

  @PostRemove
  public void deleteBook(Book book) {
    repository.deleteById(book.getId());
  }
}
