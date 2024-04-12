package com.ecommerce.onlinebookstore.repository.projection;

import java.util.List;

public interface BookView {
  String getTitle();
  double getPrice();
  String getDescription();
  List<String> getAuthors();
  List<String> getGenres();
}
