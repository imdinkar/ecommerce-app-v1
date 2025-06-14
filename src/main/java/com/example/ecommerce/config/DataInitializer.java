package com.example.ecommerce.config;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Override
    public void run(String... args) throws Exception {
        // ANTI-PATTERN: Hardcoded data initialization in production code
        initializePermanentUsers();
        initializeProducts();
    }
    
    private void initializePermanentUsers() {
        // Check if users already exist to avoid duplicates
        if (userService.getAllUsers().size() >= 50) {
            System.out.println("Permanent users already exist, skipping initialization.");
            return;
        }
        
        System.out.println("Creating 50 permanent users...");
        
        // Create 50 permanent users with predictable usernames
        String[] permanentUsers = {
            "admin1:Admin:User", "john1:John:Smith", "jane2:Jane:Doe", "mike3:Mike:Johnson", "sarah4:Sarah:Williams",
            "david5:David:Brown", "lisa6:Lisa:Jones", "chris7:Chris:Garcia", "emma8:Emma:Miller", "alex9:Alex:Davis",
            "maria10:Maria:Rodriguez", "robert11:Robert:Martinez", "jennifer12:Jennifer:Hernandez", "william13:William:Lopez", "linda14:Linda:Gonzalez",
            "james15:James:Wilson", "patricia16:Patricia:Anderson", "michael17:Michael:Thomas", "barbara18:Barbara:Taylor", "richard19:Richard:Moore",
            "susan20:Susan:Jackson", "joseph21:Joseph:Martin", "jessica22:Jessica:Lee", "thomas23:Thomas:Perez", "karen24:Karen:Thompson",
            "charles25:Charles:White", "nancy26:Nancy:Harris", "christopher27:Christopher:Sanchez", "betty28:Betty:Clark", "daniel29:Daniel:Ramirez",
            "matthew30:Matthew:Lewis", "helen31:Helen:Robinson", "anthony32:Anthony:Walker", "sandra33:Sandra:Young", "mark34:Mark:Allen",
            "donna35:Donna:King", "donald36:Donald:Wright", "carol37:Carol:Scott", "steven38:Steven:Torres", "ruth39:Ruth:Nguyen",
            "paul40:Paul:Hill", "sharon41:Sharon:Flores", "andrew42:Andrew:Green", "michelle43:Michelle:Adams", "joshua44:Joshua:Nelson",
            "laura45:Laura:Baker", "kenneth46:Kenneth:Hall", "kimberly47:Kimberly:Rivera", "kevin48:Kevin:Campbell", "deborah49:Deborah:Mitchell",
        };
        
        for (int i = 0; i < permanentUsers.length; i++) {
            String[] parts = permanentUsers[i].split(":");
            String username = parts[0];
            String firstName = parts[1];
            String lastName = parts[2];
            
            // Check if user already exists
            if (userService.getUserByUsername(username).isPresent()) {
                continue;
            }
            
            String email = username + "@example.com";
            String password = "password" + (i + 1);
            String address = (1000 + (i * 10)) + " Main Street, City " + (i % 10 + 1) + ", State " + (i % 5 + 1) + " 12345";
            String phone = "555-" + String.format("%04d", 1000 + i);
            
            try {
                User user = userService.registerNewUser(username, email, password, firstName, lastName, address, phone);
                System.out.println("Created permanent user: " + username + " / " + password);
            } catch (Exception e) {
                System.out.println("Failed to create user " + username + ": " + e.getMessage());
            }
        }
        
        System.out.println("Permanent users initialization completed.");
    }
    
    private void initializeProducts() {
        // Check if products already exist to avoid duplicates
        if (productService.getAllProducts().size() >= 50) {
            System.out.println("Products already exist, skipping initialization.");
            return;
        }
        
        // Enhanced product data with better images and descriptions
        Object[][] productData = {
            // Electronics
            {"Smartphone", "Electronics", "Latest flagship smartphone with advanced camera and long battery life", "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=300&h=300&fit=crop", 699.99, 25},
            {"Laptop", "Electronics", "High-performance laptop perfect for work and gaming", "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=300&h=300&fit=crop", 1299.99, 15},
            {"Headphones", "Electronics", "Premium wireless headphones with noise cancellation", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop", 199.99, 40},
            {"Tablet", "Electronics", "Lightweight tablet with stunning display and all-day battery", "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=300&h=300&fit=crop", 449.99, 30},
            {"Camera", "Electronics", "Professional DSLR camera for photography enthusiasts", "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=300&h=300&fit=crop", 899.99, 12},
            {"Speaker", "Electronics", "Portable Bluetooth speaker with rich, clear sound", "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=300&h=300&fit=crop", 89.99, 50},
            {"Monitor", "Electronics", "4K Ultra HD monitor for professional work", "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=300&h=300&fit=crop", 349.99, 20},
            {"Keyboard", "Electronics", "Mechanical gaming keyboard with RGB lighting", "https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=300&h=300&fit=crop", 129.99, 35},
            {"Mouse", "Electronics", "Ergonomic wireless mouse with precision tracking", "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=300&h=300&fit=crop", 59.99, 60},
            
            // Clothing
            {"T-Shirt", "Clothing", "Comfortable cotton t-shirt in various colors", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=300&h=300&fit=crop", 24.99, 100},
            {"Jeans", "Clothing", "Classic denim jeans with perfect fit", "https://images.unsplash.com/photo-1542272604-787c3835535d?w=300&h=300&fit=crop", 79.99, 75},
            {"Sneakers", "Clothing", "Stylish and comfortable sneakers for everyday wear", "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=300&h=300&fit=crop", 119.99, 45},
            {"Dress", "Clothing", "Elegant dress perfect for special occasions", "https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=300&h=300&fit=crop", 89.99, 30},
            {"Jacket", "Clothing", "Warm and stylish jacket for cold weather", "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=300&h=300&fit=crop", 149.99, 25},
            {"Boots", "Clothing", "Durable leather boots for outdoor adventures", "https://images.unsplash.com/photo-1544966503-7cc5ac882d5f?w=300&h=300&fit=crop", 179.99, 20},
            
            // Books
            {"Novel", "Books", "Bestselling fiction novel that will captivate you", "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop", 14.99, 200},
            {"Cookbook", "Books", "Delicious recipes from around the world", "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?w=300&h=300&fit=crop", 29.99, 80},
            {"Textbook", "Books", "Comprehensive educational textbook", "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=300&h=300&fit=crop", 89.99, 50},
            {"Magazine", "Books", "Monthly lifestyle and fashion magazine", "https://images.unsplash.com/photo-1586953208448-b95a79798f07?w=300&h=300&fit=crop", 4.99, 150},
            {"Dictionary", "Books", "Comprehensive English dictionary", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300&h=300&fit=crop", 39.99, 40},
            
            // Home
            {"Coffee Maker", "Home", "Automatic coffee maker for perfect morning brew", "https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=300&h=300&fit=crop", 129.99, 30},
            {"Blender", "Home", "High-speed blender for smoothies and more", "https://images.unsplash.com/photo-1570197788417-0e82375c9371?w=300&h=300&fit=crop", 89.99, 25},
            {"Microwave", "Home", "Compact microwave oven with multiple settings", "https://images.unsplash.com/photo-1574269909862-7e1d70bb8078?w=300&h=300&fit=crop", 199.99, 20},
            {"Toaster", "Home", "4-slice toaster with adjustable browning", "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop", 49.99, 40},
            {"Vacuum", "Home", "Powerful vacuum cleaner for deep cleaning", "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=300&h=300&fit=crop", 249.99, 15},
            {"Iron", "Home", "Steam iron for wrinkle-free clothes", "https://images.unsplash.com/photo-1582735689369-4fe89db7114c?w=300&h=300&fit=crop", 39.99, 35},
            
            // Sports
            {"Basketball", "Sports", "Official size basketball for indoor and outdoor play", "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=300&h=300&fit=crop", 29.99, 60},
            {"Tennis Racket", "Sports", "Professional tennis racket for all skill levels", "https://images.unsplash.com/photo-1551698618-1dfe5d97d256?w=300&h=300&fit=crop", 149.99, 25},
            {"Soccer Ball", "Sports", "FIFA approved soccer ball", "https://images.unsplash.com/photo-1614632537190-23e4b2e69c88?w=300&h=300&fit=crop", 39.99, 45},
            {"Yoga Mat", "Sports", "Non-slip yoga mat for comfortable practice", "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=300&h=300&fit=crop", 34.99, 70},
            {"Dumbbells", "Sports", "Adjustable dumbbells for home workouts", "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=300&fit=crop", 199.99, 20},
            {"Treadmill", "Sports", "Electric treadmill for cardio workouts", "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=300&fit=crop", 899.99, 8},
            
            // Beauty
            {"Lipstick", "Beauty", "Long-lasting lipstick in vibrant colors", "https://images.unsplash.com/photo-1586495777744-4413f21062fa?w=300&h=300&fit=crop", 19.99, 100},
            {"Shampoo", "Beauty", "Nourishing shampoo for healthy hair", "https://images.unsplash.com/photo-1571781926291-c477ebfd024b?w=300&h=300&fit=crop", 12.99, 80},
            {"Foundation", "Beauty", "Full coverage foundation for flawless skin", "https://images.unsplash.com/photo-1596462502278-27bfdc403348?w=300&h=300&fit=crop", 34.99, 60},
            {"Perfume", "Beauty", "Luxury fragrance with long-lasting scent", "https://images.unsplash.com/photo-1541643600914-78b084683601?w=300&h=300&fit=crop", 89.99, 40},
            {"Mascara", "Beauty", "Volumizing mascara for dramatic lashes", "https://images.unsplash.com/photo-1631214540242-3cd8c4b0b3b8?w=300&h=300&fit=crop", 24.99, 90},
            {"Moisturizer", "Beauty", "Hydrating moisturizer for all skin types", "https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=300&h=300&fit=crop", 29.99, 70}
        };
        
        for (Object[] data : productData) {
            Product product = new Product();
            product.setName((String) data[0]);
            product.setCategory((String) data[1]);
            product.setDescription((String) data[2]);
            product.setImageUrl((String) data[3]);
            product.setPrice(BigDecimal.valueOf((Double) data[4]));
            product.setStockQuantity((Integer) data[5]);
            // product.setActive(true); // Commented out as Product model doesn't have this field
            
            productService.createProduct(product);
        }
        
        System.out.println("Enhanced products with images initialized successfully!");
    }
}
