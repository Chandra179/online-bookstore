package com.ecommerce.onlinebookstore.repository.elasticsearch;

import com.ecommerce.onlinebookstore.entity.elasticsearch.BookEcs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface BookEcsRepository extends ElasticsearchRepository<BookEcs, UUID> {
}
