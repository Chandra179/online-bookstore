package com.ecommerce.onlinebookstore.entity;

import com.ecommerce.onlinebookstore.repository.listener.BookEntityListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "book")
@Document(indexName = "book")
@EntityListeners(BookEntityListener.class)
public class Book {
    @Id
    private UUID id;
    private String title;
    private String description;
    private double price;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Genre> genres;
}