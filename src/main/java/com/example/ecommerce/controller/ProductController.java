package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/active")
    public List<Product> getActiveProducts() {
        return productService.getActiveProducts();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }
    
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProducts(name);
    }
    
    // ANTI-PATTERN: Complex parameter handling without validation
    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(@RequestParam String minPrice, @RequestParam String maxPrice) {
        try {
            BigDecimal min = new BigDecimal(minPrice);
            BigDecimal max = new BigDecimal(maxPrice);
            return productService.getProductsByPriceRange(min, max);
        } catch (NumberFormatException e) {
            // ANTI-PATTERN: Poor error handling
            return productService.getAllProducts();
        }
    }
    
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
    
    // ANTI-PATTERN: Business logic in controller
    @PutMapping("/{id}/stock")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam int quantity) {
        boolean updated = productService.updateStock(id, quantity);
        if (updated) {
            return ResponseEntity.ok("Stock updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update stock");
        }
    }
    
    @GetMapping("/featured")
    public List<Product> getFeaturedProducts() {
        return productService.getFeaturedProducts();
    }
}
