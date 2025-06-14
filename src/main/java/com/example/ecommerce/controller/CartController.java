package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        
        return "cart";
    }
    
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, 
                           @RequestParam Integer quantity,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = getCurrentUser(session);
            if (user == null) {
                return "redirect:/login";
            }
            
            Cart cart = cartService.addToCart(user, productId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Product added to cart successfully!");
            
            // Redirect back to the referring page
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add product to cart: " + e.getMessage());
            return "redirect:/products";
        }
    }
    
    @PostMapping("/cart/update")
    @ResponseBody
    public ResponseEntity<?> updateCartItem(@RequestParam Long cartItemId,
                                          @RequestParam Integer quantity,
                                          HttpSession session) {
        try {
            User user = getCurrentUser(session);
            if (user == null) {
                return ResponseEntity.status(401).body("Please login first");
            }
            
            cartService.updateCartItem(user, cartItemId, quantity);
            return ResponseEntity.ok("Cart updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/cart/remove")
    @ResponseBody
    public ResponseEntity<?> removeFromCart(@RequestParam Long cartItemId,
                                          HttpSession session) {
        try {
            User user = getCurrentUser(session);
            if (user == null) {
                return ResponseEntity.status(401).body("Please login first");
            }
            
            cartService.removeFromCart(user, cartItemId);
            return ResponseEntity.ok("Item removed from cart");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/cart/clear")
    @ResponseBody
    public ResponseEntity<?> clearCart(HttpSession session) {
        try {
            User user = getCurrentUser(session);
            if (user == null) {
                return ResponseEntity.status(401).body("Please login first");
            }
            
            cartService.clearCart(user);
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    private User getCurrentUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return userService.getUserByUsername(username).orElse(null);
        }
        return null;
    }
}
