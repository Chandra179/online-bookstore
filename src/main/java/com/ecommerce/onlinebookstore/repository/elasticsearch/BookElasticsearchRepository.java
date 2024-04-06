package com.ecommerce.onlinebookstore.repository.elasticsearch;

import com.ecommerce.onlinebookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface BookElasticsearchRepository extends ElasticsearchRepository<Book, UUID> {

    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title^3\", \"authors.name^2\", \"genres.name\"], \"type\": \"best_fields\"}}")
    Page<Book> search(String keyword, Pageable pageable);
}