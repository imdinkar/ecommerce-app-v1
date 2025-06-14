package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CheckoutController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        Cart cart = cartService.getCartByUser(user);
        if (cart == null || cart.getCartItems().isEmpty()) {
            return "redirect:/cart";
        }
        
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        
        return "checkout";
    }
    
    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String shippingAddress,
                                @RequestParam String paymentMethod,
                                @RequestParam String cardNumber,
                                @RequestParam String cardName,
                                @RequestParam String expiryDate,
                                @RequestParam String cvv,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            User user = getCurrentUser(session);
            if (user == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Please login to continue checkout.");
                return "redirect:/login";
            }
            
            // Validate required fields
            if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Shipping address is required.");
                return "redirect:/checkout";
            }
            
            if (cardNumber == null || cardNumber.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Card number is required.");
                return "redirect:/checkout";
            }
            
            if (cardName == null || cardName.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Cardholder name is required.");
                return "redirect:/checkout";
            }
            
            if (expiryDate == null || expiryDate.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Expiry date is required.");
                return "redirect:/checkout";
            }
            
            if (cvv == null || cvv.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "CVV is required.");
                return "redirect:/checkout";
            }
            
            // Create order from cart
            Order order = orderService.createOrderFromCart(user, shippingAddress);
            
            // Simulate payment processing with card details
            boolean paymentSuccess = processPaymentWithCard(paymentMethod, order, cardNumber, cardName, expiryDate, cvv);
            
            if (paymentSuccess) {
                order.setStatus(Order.OrderStatus.CONFIRMED);
                orderService.updateOrderStatus(order.getId(), Order.OrderStatus.CONFIRMED);
                
                // Store order ID in session for confirmation page
                session.setAttribute("lastOrderId", order.getId());
                
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Order placed successfully! Order ID: " + order.getId());
                return "redirect:/order-confirmation/" + order.getId();
            } else {
                // Payment failed - cancel order and restore stock
                orderService.cancelOrder(order.getId());
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Payment failed. Please check your card details and try again.");
                return "redirect:/checkout";
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Checkout failed: " + e.getMessage());
            return "redirect:/checkout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "An unexpected error occurred during checkout. Please try again.");
            return "redirect:/checkout";
        }
    }
    
    @GetMapping("/order-confirmation/{orderId}")
    public String orderConfirmation(@PathVariable Long orderId, HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        Order order = orderService.getOrderById(orderId).orElse(null);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return "redirect:/orders";
        }
        
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        
        return "order-confirmation";
    }
    
    @GetMapping("/orders")
    public String viewOrders(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        model.addAttribute("user", user);
        
        return "orders";
    }
    
    @GetMapping("/orders/{orderId}")
    public String viewOrder(@PathVariable Long orderId, HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        Order order = orderService.getOrderById(orderId).orElse(null);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return "redirect:/orders";
        }
        
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        
        return "order-details";
    }
    
    // Dummy payment processing - always returns true for demo
    private boolean processPayment(String paymentMethod, Order order) {
        // Simulate payment processing delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // For demo purposes, randomly succeed/fail 90% success rate
        return Math.random() > 0.1;
    }
    
    // Payment processing with card details
    private boolean processPaymentWithCard(String paymentMethod, Order order, String cardNumber, String cardName, String expiryDate, String cvv) {
        // Simulate payment processing
        System.out.println("Processing payment for Order #" + order.getId());
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Order Total: $" + order.getTotalAmount());
        
        // Basic validation
        if (cardNumber == null || cardNumber.replace(" ", "").length() < 13) {
            return false;
        }
        
        if (cvv == null || cvv.length() < 3) {
            return false;
        }
        
        if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        
        // Simulate payment success (95% success rate for demo)
        return Math.random() > 0.05;
    }
    
    private User getCurrentUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return userService.getUserByUsername(username).orElse(null);
        }
        return null;
    }
}
