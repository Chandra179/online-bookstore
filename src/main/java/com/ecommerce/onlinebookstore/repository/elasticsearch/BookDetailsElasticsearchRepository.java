package com.ecommerce.onlinebookstore.repository.elasticsearch;

import com.ecommerce.onlinebookstore.entity.elasticsearch.BookDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface BookDetailsElasticsearchRepository extends ElasticsearchRepository<BookDetails, UUID> {
}
