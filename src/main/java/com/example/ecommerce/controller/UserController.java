package com.example.ecommerce.controller;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // ANTI-PATTERN: Exposing all user data including sensitive information
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    // ANTI-PATTERN: No input validation
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    
    // ANTI-PATTERN: Authentication logic in controller
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean authenticated = userService.authenticateUser(username, password);
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
    
    // ANTI-PATTERN: Too many parameters
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam String username,
                                       @RequestParam String email,
                                       @RequestParam String password,
                                       @RequestParam String firstName,
                                       @RequestParam String lastName,
                                       @RequestParam String address,
                                       @RequestParam String phone) {
        User user = userService.registerNewUser(username, email, password, firstName, lastName, address, phone);
        return ResponseEntity.ok(user);
    }
    
    // ANTI-PATTERN: Exposing internal search functionality
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String name) {
        return userService.searchUsersByName(name);
    }
}
