package com.ecommerce.onlinebookstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ecommerce.onlinebookstore.repository.jpa")
@EnableElasticsearchRepositories(basePackages = "com.ecommerce.onlinebookstore.repository.elasticsearch")
public class RepositoryConfig {
}