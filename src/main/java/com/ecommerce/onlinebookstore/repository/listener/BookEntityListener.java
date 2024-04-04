package com.ecommerce.onlinebookstore.repository.listener;

import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.repository.elasticsearch.BookElasticsearchRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEntityListener {

    private static BookElasticsearchRepository bookElasticsearchRepository;

    @Autowired
    public void setBookElasticsearchRepository(BookElasticsearchRepository bookElasticsearchRepository) {
        BookEntityListener.bookElasticsearchRepository = bookElasticsearchRepository;
    }

    @PostPersist
    @PostUpdate
    public void saveBook(Book book) {
        bookElasticsearchRepository.save(book);
    }

    @PostRemove
    public void deleteBook(Book book) {
        bookElasticsearchRepository.deleteById(book.getId());
    }
}