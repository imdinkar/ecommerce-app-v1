package com.example.ecommerce.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Calculate total amount
    public BigDecimal getTotalAmount() {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Calculate total items count
    public int getTotalItems() {
        return cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
