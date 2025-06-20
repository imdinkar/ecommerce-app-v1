package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActiveTrue();
    List<Product> findByCategory(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // ANTI-PATTERN: N+1 query problem potential
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0")
    List<Product> findAvailableProducts();
}
