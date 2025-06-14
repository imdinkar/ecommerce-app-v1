package com.example.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(unique = true)
    private String username;
    
    @Email
    @Column(unique = true)
    private String email;
    
    // ANTI-PATTERN: Storing password in plain text
    @NotBlank
    private String password;
    
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    // ANTI-PATTERN: God object - too many responsibilities
    private String creditCardNumber;
    private String creditCardExpiry;
    private String creditCardCvv;
    
    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    public User(String username, String email, String password) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public String getCreditCardNumber() { return creditCardNumber; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }
    
    public String getCreditCardExpiry() { return creditCardExpiry; }
    public void setCreditCardExpiry(String creditCardExpiry) { this.creditCardExpiry = creditCardExpiry; }
    
    public String getCreditCardCvv() { return creditCardCvv; }
    public void setCreditCardCvv(String creditCardCvv) { this.creditCardCvv = creditCardCvv; }
}
