package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/retailer")
public class RetailerController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("user", user);
        
        return "retailer/dashboard";
    }
    
    @GetMapping("/products")
    public String manageProducts(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("user", user);
        
        return "retailer/products";
    }
    
    @GetMapping("/products/add")
    public String addProductForm(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("product", new Product());
        model.addAttribute("user", user);
        
        return "retailer/add-product";
    }
    
    @PostMapping("/products/add")
    public String addProduct(@RequestParam String name,
                           @RequestParam String description,
                           @RequestParam BigDecimal price,
                           @RequestParam Integer stockQuantity,
                           @RequestParam String category,
                           @RequestParam(required = false) String imageUrl,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = getCurrentUser(session);
            if (user == null) {
                return "redirect:/login";
            }
            
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);
            product.setCategory(category);
            product.setImageUrl(imageUrl != null && !imageUrl.trim().isEmpty() ? 
                              imageUrl : "https://via.placeholder.com/300x300?text=" + name.replace(" ", "+"));
            product.setIsActive(true);
            
            productService.createProduct(product);
            
            redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
            return "redirect:/retailer/products";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding product: " + e.getMessage());
            return "redirect:/retailer/products/add";
        }
    }
    
    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        
        Optional<Product> productOpt = productService.getProductById(id);
        if (!productOpt.isPresent()) {
            return "redirect:/retailer/products";
        }
        
        Product product = productOpt.get();
        
        model.addAttribute("product", product);
        model.addAttribute("user", user);
        
        return "retailer/edit-product";
    }
    
    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam BigDecimal price,
                            @RequestParam Integer stockQuantity,
                            @RequestParam String category,
                            @RequestParam(required = false) String imageUrl,
                            @RequestParam Boolean isActive,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            User user = getCurrentUser(session);
            if (user == null) {
                return "redirect:/login";
            }
            
            Optional<Product> productOpt = productService.getProductById(id);
            if (!productOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
                return "redirect:/retailer/products";
            }
            
            Product product = productOpt.get();
            
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);
            product.setCategory(category);
            product.setImageUrl(imageUrl != null && !imageUrl.trim().isEmpty() ? 
                              imageUrl : product.getImageUrl());
            product.setIsActive(isActive);
            
            productService.updateProduct(product);
            
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
            return "redirect:/retailer/products";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product: " + e.getMessage());
            return "redirect:/retailer/products/edit/" + id;
        }
    }
    
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, 
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = getCurrentUser(session);
            if (user == null) {
                return "redirect:/login";
            }
            
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product: " + e.getMessage());
        }
        
        return "redirect:/retailer/products";
    }
    
    private User getCurrentUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return userService.getUserByUsername(username).orElse(null);
        }
        return null;
    }
}
