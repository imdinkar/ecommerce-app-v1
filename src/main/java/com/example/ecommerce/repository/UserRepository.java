package com.example.ecommerce.repository;

import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByIsActiveTrue();
    
    // ANTI-PATTERN: Complex native queries in repository
    @Query(value = "SELECT * FROM users WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(last_name) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    List<User> findUsersByNameContaining(String name);
    
    // ANTI-PATTERN: Business logic in repository
    @Query("SELECT u FROM User u WHERE u.isActive = true AND LENGTH(u.username) > 5")
    List<User> findActiveUsersWithLongUsernames();
}
