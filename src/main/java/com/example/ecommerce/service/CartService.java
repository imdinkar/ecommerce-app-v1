package com.example.ecommerce.service;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductService productService;
    
    public Cart getOrCreateCart(User user) {
        Optional<Cart> existingCart = cartRepository.findByUserWithItems(user);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }
    
    public Cart addToCart(User user, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(user);
        Optional<Product> productOpt = productService.getProductById(productId);
        
        if (!productOpt.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        
        Product product = productOpt.get();
        
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);
        
        if (existingItem.isPresent()) {
            // Update existing cart item
            CartItem cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            
            if (product.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Insufficient stock");
            }
            
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cartItemRepository.save(cartItem);
            
            cart.getCartItems().add(cartItem);
        }
        
        return cartRepository.save(cart);
    }
    
    public Cart updateCartItem(User user, Long cartItemId, Integer quantity) {
        Cart cart = getOrCreateCart(user);
        
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }
        
        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
            cart.getCartItems().remove(cartItem);
        } else {
            if (cartItem.getProduct().getStockQuantity() < quantity) {
                throw new RuntimeException("Insufficient stock");
            }
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
        
        return cartRepository.save(cart);
    }
    
    public void removeFromCart(User user, Long cartItemId) {
        Cart cart = getOrCreateCart(user);
        
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }
        
        cartItemRepository.delete(cartItem);
        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
    }
    
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cartItemRepository.deleteByCart(cart);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
    
    public Cart getCartByUser(User user) {
        return cartRepository.findByUserWithItems(user).orElse(null);
    }
}
