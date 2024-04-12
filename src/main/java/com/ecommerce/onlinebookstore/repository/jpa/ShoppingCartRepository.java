package com.ecommerce.onlinebookstore.repository.jpa;

import com.ecommerce.onlinebookstore.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
  @Query("SELECT sc FROM ShoppingCart sc WHERE sc.userAccount.id = :userId AND sc.id = :cartId")
  Optional<ShoppingCart> findByUserAccountIdAndCartId(@Param("userId") UUID userId, @Param("cartId") UUID cartId);
}
