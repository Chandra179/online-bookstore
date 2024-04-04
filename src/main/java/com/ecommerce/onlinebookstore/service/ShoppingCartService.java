package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.entity.ShoppingCart;
import com.ecommerce.onlinebookstore.repository.jpa.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> saveCartTask;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, TaskScheduler taskScheduler) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.taskScheduler = taskScheduler;
    }

    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public ShoppingCart addBookToCart(ShoppingCart shoppingCart, Book book) {
        debounceSaveCart(shoppingCart, book, true);
        return shoppingCart;
    }

    public ShoppingCart removeBookFromCart(ShoppingCart shoppingCart, Book book) {
        debounceSaveCart(shoppingCart, book, false);
        return shoppingCart;
    }

    private void debounceSaveCart(ShoppingCart shoppingCart, Book book, boolean isAdding) {
        if (saveCartTask != null) {
            saveCartTask.cancel(false);
        }

        // Retrieve the existing ShoppingCart from the database
        ShoppingCart existingCart = getShoppingCartById(shoppingCart.getId())
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found"));

        // Update the existing ShoppingCart with the new book
        if (isAdding) {
            existingCart.getBooks().add(book);
        } else {
            existingCart.getBooks().remove(book);
        }

        // Schedule a new save operation for the updated ShoppingCart
        saveCartTask = taskScheduler.schedule(() -> shoppingCartRepository.save(existingCart),
                java.time.Instant.now().plusSeconds(5));
    }
}