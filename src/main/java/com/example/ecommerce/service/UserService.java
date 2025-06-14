package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    // ANTI-PATTERN: Field injection instead of constructor injection
    @Autowired
    private UserRepository userRepository;
    
    // ANTI-PATTERN: No exception handling
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    // ANTI-PATTERN: Business logic mixed with data access
    public boolean authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            // ANTI-PATTERN: Plain text password comparison
            return user.get().getPassword().equals(password);
        }
        return false;
    }
    
    // ANTI-PATTERN: Method doing too many things
    public User registerNewUser(String username, String email, String password, 
                               String firstName, String lastName, String address, String phone) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // ANTI-PATTERN: No password hashing
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhoneNumber(phone);
        
        // ANTI-PATTERN: No validation
        return userRepository.save(user);
    }
    
    // ANTI-PATTERN: Exposing repository functionality
    public List<User> searchUsersByName(String name) {
        return userRepository.findUsersByNameContaining(name);
    }
}
