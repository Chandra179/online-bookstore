package com.ecommerce.onlinebookstore.service;

import com.ecommerce.onlinebookstore.dto.CartItem;
import com.ecommerce.onlinebookstore.entity.ShoppingCart;
import com.ecommerce.onlinebookstore.entity.UserAccount;
import com.ecommerce.onlinebookstore.repository.jpa.BookRepository;
import com.ecommerce.onlinebookstore.repository.jpa.ShoppingCartRepository;
import com.ecommerce.onlinebookstore.repository.jpa.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class ShoppingCartService {
  private final ShoppingCartRepository shoppingCartRepository;
  private final BookRepository bookRepository;
  private final TaskScheduler taskScheduler;
  private final UserAccountRepository userAccountRepository;
  private ScheduledFuture<?> saveCartTask;

  @Autowired
  public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, BookRepository bookRepository, TaskScheduler taskScheduler, UserAccountRepository userAccountRepository) {
    this.shoppingCartRepository = shoppingCartRepository;
    this.bookRepository = bookRepository;
    this.taskScheduler = taskScheduler;
    this.userAccountRepository = userAccountRepository;
  }

  public void addItemToCart(CartItem cartItem) {
    debounceSaveCart(cartItem,true);
  }

  private void debounceSaveCart(CartItem cartItem, boolean isAdding) {
    if (saveCartTask != null) {
      saveCartTask.cancel(false);
    }

    ShoppingCart cart = shoppingCartRepository
      .findByUserAccountIdAndCartId(UUID.fromString(cartItem.getCustomerId()), UUID.fromString(cartItem.getCartId()))
      .orElseGet(() -> {
        UserAccount userAccount = userAccountRepository.findById(UUID.fromString(cartItem.getCustomerId())).orElseThrow();
        return ShoppingCart.builder().customerAccount(userAccount).books(new ArrayList<>()).build();
      });

    // Fetch the book from the database
    var book = bookRepository.findById(UUID.fromString(cartItem.getBookId())).orElseThrow();

    // Update the existing ShoppingCart with the new book
    if (isAdding) {
      cart.getBooks().add(book);
    } else {
      if (!cart.getBooks().isEmpty()) {
        cart.getBooks().remove(book);
      }
    }

    // Schedule a new save operation for the updated ShoppingCart
    saveCartTask = taskScheduler.schedule(() -> shoppingCartRepository.save(cart),
      java.time.Instant.now().plusSeconds(5));
  }
}
