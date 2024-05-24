package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.dto.CartItem;
import com.ecommerce.onlinebookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    service.addItemToCart(cartItem);
  }
}
