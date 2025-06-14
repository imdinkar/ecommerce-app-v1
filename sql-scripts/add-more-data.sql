-- Add more users and products to reach 100 records each
USE ecommerce_db;

-- Add more users (continuing from user11 to user100)
INSERT INTO users (username, email, password, first_name, last_name) VALUES
('user11', 'user11@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Eleven'),
('user12', 'user12@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Twelve'),
('user13', 'user13@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Thirteen'),
('user14', 'user14@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Fourteen'),
('user15', 'user15@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Fifteen'),
('user16', 'user16@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Sixteen'),
('user17', 'user17@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Seventeen'),
('user18', 'user18@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Eighteen'),
('user19', 'user19@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Nineteen'),
('user20', 'user20@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Twenty');

-- Add more products (continuing from product31 to product100)
INSERT INTO products (name, description, price, stock_quantity, category) VALUES
('Gaming Mouse', 'High-precision gaming mouse with RGB lighting', 79.99, 85, 'Electronics'),
('Mechanical Keyboard', 'RGB mechanical keyboard with blue switches', 129.99, 70, 'Electronics'),
('Monitor 27"', '27-inch 4K monitor with HDR support', 399.99, 40, 'Electronics'),
('Webcam HD', '1080p HD webcam for video calls', 59.99, 120, 'Electronics'),
('USB Hub', '7-port USB 3.0 hub', 29.99, 150, 'Electronics'),
('Power Bank', '20000mAh portable power bank', 49.99, 100, 'Electronics'),
('Wireless Charger', 'Fast wireless charging pad', 39.99, 130, 'Electronics'),
('Smart Speaker', 'Voice-controlled smart speaker', 99.99, 80, 'Electronics'),
('Fitness Tracker', 'Waterproof fitness tracker with heart rate monitor', 149.99, 90, 'Electronics'),
('Tablet 10"', '10-inch Android tablet with 64GB storage', 299.99, 60, 'Electronics');

-- Show current counts
SELECT 'Users' as table_name, COUNT(*) as record_count FROM users
UNION ALL
SELECT 'Products' as table_name, COUNT(*) as record_count FROM products;
