package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.dto.BookCreationRequest;
import com.ecommerce.onlinebookstore.entity.Author;
import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.entity.Genre;
import com.ecommerce.onlinebookstore.entity.elasticsearch.BookEcs;
import com.ecommerce.onlinebookstore.repository.jpa.AuthorRepository;
import com.ecommerce.onlinebookstore.repository.jpa.BookRepository;
import com.ecommerce.onlinebookstore.repository.jpa.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;
  private final ElasticsearchOperations elasticsearchOperations;

  @Autowired
  public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, ElasticsearchOperations elasticsearchOperations) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
    this.elasticsearchOperations = elasticsearchOperations;
  }

  public void addBook(BookCreationRequest bookDto) {
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

    Book book = new Book();
    book.setId(UUID.randomUUID());
    book.setTitle(bookDto.getTitle());
    book.setAuthors(List.of(author));
    book.setGenres(List.of(genre));
    book.setPrice(bookDto.getPrice());
    bookRepository.save(book);
  }

  public Page<BookEcs> search(String text, Pageable pageable) {
    Query query = NativeQuery.builder()
      .withQuery(q -> q
        .bool(b -> b
          .should(s -> s
            .multiMatch(m -> m
              .query(text)
              .fields("bookTitle")
            )
          )
          .should(s -> s
            .nested(n -> n
              .path("authors")
              .query(q2 -> q2
                .match(m -> m
                  .field("authors.name")
                  .query(text)
                )
              )
            )
          )
          .should(s -> s
            .nested(n -> n
              .path("genres")
              .query(q2 -> q2
                .match(m -> m
                  .field("genres.name")
                  .query(text)
                )
              )
            )
          )
        )
      )
      .withPageable(pageable)
      .build();

    SearchHits<BookEcs> searchHits = elasticsearchOperations.search(query, BookEcs.class);
    return SearchHitSupport.searchPageFor(searchHits, pageable).map(SearchHit::getContent);
  }
}
