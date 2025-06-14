package com.example.ecommerce.repository;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems ci LEFT JOIN FETCH ci.product WHERE c.user = ?1")
    Optional<Cart> findByUserWithItems(User user);
    
    void deleteByUser(User user);
}
