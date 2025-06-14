USE ecommerce_db;

-- Clear existing data
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM products;
DELETE FROM users;

-- Reset auto increment
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE products AUTO_INCREMENT = 1;
ALTER TABLE orders AUTO_INCREMENT = 1;
ALTER TABLE order_items AUTO_INCREMENT = 1;

-- Insert 100 users
INSERT INTO users (username, email, password, first_name, last_name) VALUES
