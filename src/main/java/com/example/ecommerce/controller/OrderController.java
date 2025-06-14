package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            List<Order> orders = orderService.getOrdersByUser(user.get());
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.notFound().build();
    }
    
    // ANTI-PATTERN: Complex request handling without proper DTO
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestParam Long userId,
                                           @RequestParam String shippingAddress,
                                           @RequestBody Map<String, Object> orderData) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            // ANTI-PATTERN: Unsafe casting and no validation
            Map<Long, Integer> productQuantities = new HashMap<>();
            @SuppressWarnings("unchecked")
            Map<String, Integer> products = (Map<String, Integer>) orderData.get("products");
            
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                try {
                    Long productId = Long.parseLong(entry.getKey());
                    productQuantities.put(productId, entry.getValue());
                } catch (NumberFormatException e) {
                    // ANTI-PATTERN: Silently ignoring errors
                    continue;
                }
            }
            
            // This endpoint is deprecated - use checkout flow instead
            return ResponseEntity.badRequest().body("Use /checkout/process endpoint instead");
        }
        return ResponseEntity.badRequest().body("Invalid user");
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            Order updatedOrder = orderService.updateOrderStatus(id, orderStatus);
            if (updatedOrder != null) {
                return ResponseEntity.ok(updatedOrder);
            }
        } catch (IllegalArgumentException e) {
            // ANTI-PATTERN: Poor error handling
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        Order cancelledOrder = orderService.cancelOrder(id);
        if (cancelledOrder != null) {
            return ResponseEntity.ok("Order cancelled successfully");
        } else {
            return ResponseEntity.badRequest().body("Cannot cancel order");
        }
    }
}
