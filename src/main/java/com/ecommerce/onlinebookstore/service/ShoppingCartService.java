package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.entity.Book;
import com.ecommerce.onlinebookstore.entity.ShoppingCart;
import com.ecommerce.onlinebookstore.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public ShoppingCart addBookToCart(ShoppingCart shoppingCart, Book book) {
        shoppingCart.getBooks().add(book);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart removeBookFromCart(ShoppingCart shoppingCart, Book book) {
        shoppingCart.getBooks().remove(book);
        return shoppingCartRepository.save(shoppingCart);
    }
}