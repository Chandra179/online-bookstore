package com.ecommerce.onlinebookstore.entity;

import com.ecommerce.onlinebookstore.entity.listener.BookEntityListener;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "book")
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
    private List<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

}
