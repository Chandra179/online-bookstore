package com.ecommerce.onlinebookstore.entity.elasticsearch;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document(indexName = "bookdetails")
public class BookDetails {

  @Id
  @Field(type = FieldType.Text)
  private String id;

  @Field(type = FieldType.Text)
  private UUID bookId;

  @Field(type = FieldType.Nested)
  private List<Authors> authors;

  @Field(type = FieldType.Text)
  private String bookTitle;

  @Field(type = FieldType.Text)
  private double price;

  @Field(type = FieldType.Nested)
  private List<Genre> genres;

  @Builder
  @Getter
  public static class Authors {
    @Field(type = FieldType.Text)
    private Long id;

    @Field(type = FieldType.Text)
    private String name;
  }

  @Builder
  @Getter
  public static class Genre { // New static nested class
    @Field(type = FieldType.Text)
    private String name;
  }
}
