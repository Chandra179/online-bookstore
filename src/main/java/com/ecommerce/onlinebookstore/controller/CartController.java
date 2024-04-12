package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.dto.BookCreationRequest;
import com.ecommerce.onlinebookstore.dto.CartItem;
import com.ecommerce.onlinebookstore.entity.elasticsearch.BookEcs;
import com.ecommerce.onlinebookstore.repository.projection.BookView;
import com.ecommerce.onlinebookstore.service.BookService;
import com.ecommerce.onlinebookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

  private final ShoppingCartService service;

  @Autowired
  public CartController(ShoppingCartService service) {
    this.service = service;
  }

  @PostMapping
  public void addItemToCart(@RequestBody CartItem cartItem) {
    service.addBookToCart(cartItem);
  }
}
