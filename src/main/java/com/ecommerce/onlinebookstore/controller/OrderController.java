package com.ecommerce.onlinebookstore.controller;

import com.ecommerce.onlinebookstore.dto.OrderItem;
import com.ecommerce.onlinebookstore.entity.Genre;
import com.ecommerce.onlinebookstore.repository.jpa.GenreRepository;
import com.ecommerce.onlinebookstore.repository.jpa.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderRepository repository;

  @Autowired
  public OrderController(OrderRepository orderRepository) {
    this.repository = orderRepository;
  }

  @GetMapping
  public void placeOrder(@RequestBody OrderItem orderItem) {
    repository.save(null);
  }

}
