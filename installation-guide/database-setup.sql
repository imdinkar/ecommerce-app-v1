-- =====================================================
-- E-Commerce Application Database Setup Script
-- =====================================================
-- This script sets up the MySQL database for the e-commerce application
-- Run this script as MySQL root user

-- Create database
DROP DATABASE IF EXISTS ecommerce_db;
CREATE DATABASE ecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create application user (optional - you can use root)
DROP USER IF EXISTS 'ecommerce_user'@'localhost';
CREATE USER 'ecommerce_user'@'localhost' IDENTIFIED BY 'Password123!';

-- Grant privileges
GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecommerce_user'@'localhost';
FLUSH PRIVILEGES;

-- Use the database
USE ecommerce_db;

-- Verify database creation
SELECT 'Database ecommerce_db created successfully!' as Status;

-- Show current database
SELECT DATABASE() as 'Current Database';

-- =====================================================
-- NOTE: Table creation is handled automatically by Hibernate
-- The following tables will be created when the application starts:
-- 
-- 1. users - User accounts (customers and retailers)
-- 2. products - Product catalog
-- 3. cart_items - Shopping cart items
-- 4. orders - Customer orders
-- 5. order_items - Individual items in orders
-- 
-- Sample data is automatically populated by DataInitializer.java
-- =====================================================

-- Optional: Create tables manually (if needed)
-- Uncomment the following section if you want to create tables manually

/*
-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role ENUM('CUSTOMER', 'RETAILER') DEFAULT 'CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT DEFAULT 0,
    category VARCHAR(100) NOT NULL,
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Cart items table
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_product (user_id, product_id)
);

-- Orders table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    shipping_address TEXT NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Order items table
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Insert sample users
INSERT INTO users (email, password, first_name, last_name, role) VALUES
('customer@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'John', 'Customer', 'CUSTOMER'),
('retailer@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Jane', 'Retailer', 'RETAILER');

-- Note: Password for both users is 'password123'
*/

-- =====================================================
-- Database Setup Complete
-- =====================================================

-- Show database information
SHOW TABLES;
SELECT COUNT(*) as 'Total Tables' FROM information_schema.tables WHERE table_schema = 'ecommerce_db';

-- Show user privileges
SHOW GRANTS FOR 'ecommerce_user'@'localhost';

-- Final status
SELECT 
    'Database setup completed successfully!' as 'Status',
    'You can now start the Spring Boot application' as 'Next Step',
    'Tables will be created automatically by Hibernate' as 'Note';
