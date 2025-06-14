package com.example.ecommerce.service;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CartService cartService;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    // Create order from cart
    public Order createOrderFromCart(User user, String shippingAddress) {
        Cart cart = cartService.getCartByUser(user);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setStatus(Order.OrderStatus.PENDING);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        // Convert cart items to order items
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            
            // Check stock availability
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            
            order.getOrderItems().add(orderItem);
            totalAmount = totalAmount.add(orderItem.getTotalPrice());
            
            // Update product stock
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }
        
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        
        // Clear the cart after successful order
        cartService.clearCart(user);
        
        return savedOrder;
    }
    
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        return null;
    }
    
    // ANTI-PATTERN: Returning null instead of Optional
    public Order cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getStatus() == Order.OrderStatus.PENDING) {
                order.setStatus(Order.OrderStatus.CANCELLED);
                order.setUpdatedAt(LocalDateTime.now());
                return orderRepository.save(order);
            }
        }
        return null;
    }
}
