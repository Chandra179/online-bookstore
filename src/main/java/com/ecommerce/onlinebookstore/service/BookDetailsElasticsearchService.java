package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.entity.elasticsearch.BookDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BookDetailsElasticsearchService {

  private final ElasticsearchOperations elasticsearchOperations;

  @Autowired
  public BookDetailsElasticsearchService(ElasticsearchOperations elasticsearchOperations) {
    this.elasticsearchOperations = elasticsearchOperations;
  }

  public Page<BookDetails> search(String text, Pageable pageable) {
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

    SearchHits<BookDetails> searchHits = elasticsearchOperations.search(query, BookDetails.class);
    return SearchHitSupport.searchPageFor(searchHits, pageable).map(SearchHit::getContent);
  }
}
