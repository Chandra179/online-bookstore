package com.ecommerce.onlinebookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class BookCreationRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String title;

  @JsonProperty("author")
  private String authorName;

  @JsonProperty("genre")
  private String genreName;

  @JsonProperty("price")
  private double price;
}
