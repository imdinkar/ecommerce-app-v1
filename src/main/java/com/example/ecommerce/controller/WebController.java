package com.example.ecommerce.controller;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class WebController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // Get featured products (first 8 products)
        model.addAttribute("featuredProducts", productService.getAllProducts().stream().limit(8).collect(java.util.stream.Collectors.toList()));
        
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.getUserByUsername(username).orElse(null);
            model.addAttribute("user", user);
        }
        
        return "index";
    }
    
    @GetMapping("/products")
    public String products(Model model, HttpSession session) {
        model.addAttribute("products", productService.getAllProducts());
        
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.getUserByUsername(username).orElse(null);
            model.addAttribute("user", user);
        }
        
        return "products";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserByUsername(username).orElse(null);
            if (user != null && user.getPassword().equals(password)) {
                session.setAttribute("username", username);
                session.setAttribute("userId", user.getId());
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
                return "redirect:/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Login failed: " + e.getMessage());
            return "redirect:/login";
        }
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String address,
                                @RequestParam String phoneNumber,
                                RedirectAttributes redirectAttributes) {
        try {
            userService.registerNewUser(username, email, password, firstName, lastName, address, phoneNumber);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
