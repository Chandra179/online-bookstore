package com.ecommerce.onlinebookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class OrderItem implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("book_id")
  private String bookId;

  @JsonProperty("book_qty")
  private String bookQty;

  @JsonProperty("customer_id")
  private double customerId;
}
