package com.ecommerce.onlinebookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class CartItem implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String title;

  @JsonProperty("customer_id")
  private String customerId;

  @JsonProperty("book_id")
  private String bookId;

  @JsonProperty("cart_id")
  private String cartId;

  @JsonProperty("book_qty")
  private int bookQty;
}
