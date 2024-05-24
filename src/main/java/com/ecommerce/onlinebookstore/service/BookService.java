package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.dto.BookItem;
import com.ecommerce.onlinebookstore.entity.Author;
import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.entity.Genre;
import com.ecommerce.onlinebookstore.entity.Inventory;
import com.ecommerce.onlinebookstore.entity.elasticsearch.BookEcs;
import com.ecommerce.onlinebookstore.repository.jpa.AuthorRepository;
import com.ecommerce.onlinebookstore.repository.jpa.BookRepository;
import com.ecommerce.onlinebookstore.repository.jpa.GenreRepository;
import com.ecommerce.onlinebookstore.repository.jpa.InventoryRepository;
import com.ecommerce.onlinebookstore.repository.projection.BookView;
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
  private final InventoryRepository inventoryRepository;

  @Autowired
  public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository,
                     ElasticsearchOperations elasticsearchOperations, InventoryRepository inventoryRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
    this.elasticsearchOperations = elasticsearchOperations;
    this.inventoryRepository = inventoryRepository;
  }

  public void createBook(BookItem bookDto) {
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
    Book savedBook = bookRepository.save(book);

    inventoryRepository.save(Inventory.builder()
      .book(savedBook)
      .quantity(bookDto.getBookQty())
      .build()
    );
  }

  /**
   * This method is used to search for books in the Elasticsearch index.
   * It constructs a query that matches the input text against the book title, author names, and genre names.
   * The search is performed in a "should" clause, meaning that the results can match any of the fields.
   * The results are then paginated according to the Pageable object passed as an argument.
   *
   * @param text The text to search for in the book title, author names, and genre names.
   * @param pageable The pagination information.
   * @return A Page of BookEcs objects that match the search criteria.
   */
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

  /**
   * This method is used to search for books in the Elasticsearch index based on the book title and genre.
   * It constructs a query that matches the input title and genre exactly.
   * The search is performed in a "must" clause, meaning that the results must match both the title and genre fields.
   * The results are then paginated according to the Pageable object passed as an argument.
   *
   * @param title The title of the book to search for.
   * @param genre The genre of the book to search for.
   * @param pageable The pagination information.
   * @return A Page of BookEcs objects that match the search criteria.
   */
  public Page<BookEcs> search(String title, String genre, Pageable pageable) {
    Query query = NativeQuery.builder()
      .withQuery(q -> q
        .bool(b -> b
          .must(s -> s
            .match(m -> m
              .field("bookTitle")
              .query(title)
            )
          )
          .must(s -> s
            .nested(n -> n
              .path("genres")
              .query(q2 -> q2
                .match(m -> m
                  .field("genres.name")
                  .query(genre)
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

  public BookView getBook(UUID id) {
    return bookRepository.findBookById(id);
  }
}
