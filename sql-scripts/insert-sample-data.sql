-- Insert sample data for ecommerce application
USE ecommerce_db;

-- Insert sample users (password is 'password' hashed with bcrypt)
INSERT INTO users (username, email, password, first_name, last_name) VALUES
('john_doe', 'john@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'John', 'Doe'),
('jane_smith', 'jane@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'Jane', 'Smith'),
('bob_johnson', 'bob@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'Bob', 'Johnson'),
('alice_brown', 'alice@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'Alice', 'Brown'),
('charlie_wilson', 'charlie@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'Charlie', 'Wilson');

-- Insert sample products
INSERT INTO products (name, description, price, stock_quantity, category) VALUES
('MacBook Pro 16"', 'Apple MacBook Pro with M2 chip, 16GB RAM, 512GB SSD', 2499.99, 25, 'Electronics'),
('iPhone 14 Pro', 'Apple iPhone 14 Pro with 128GB storage', 999.99, 50, 'Electronics'),
('Samsung Galaxy S23', 'Samsung Galaxy S23 with 256GB storage', 799.99, 40, 'Electronics'),
('Dell XPS 13', 'Dell XPS 13 laptop with Intel i7 processor', 1299.99, 30, 'Electronics'),
('iPad Air', 'Apple iPad Air with 64GB storage', 599.99, 35, 'Electronics'),
('AirPods Pro', 'Apple AirPods Pro with noise cancellation', 249.99, 100, 'Electronics'),
('Sony WH-1000XM4', 'Sony wireless noise-canceling headphones', 349.99, 60, 'Electronics'),
('Canon EOS R5', 'Canon mirrorless camera with 45MP sensor', 3899.99, 15, 'Electronics'),
('Nintendo Switch', 'Nintendo Switch gaming console', 299.99, 80, 'Electronics'),
('PlayStation 5', 'Sony PlayStation 5 gaming console', 499.99, 20, 'Electronics'),
('Running Shoes', 'Nike Air Max running shoes', 129.99, 150, 'Sports'),
('Yoga Mat', 'Premium yoga mat with carrying strap', 39.99, 200, 'Sports'),
('Dumbbell Set', '20lb adjustable dumbbell set', 89.99, 75, 'Sports'),
('Coffee Maker', 'Keurig K-Elite coffee maker', 169.99, 45, 'Appliances'),
('Blender', 'Vitamix high-performance blender', 449.99, 30, 'Appliances'),
('Air Fryer', 'Ninja air fryer with 4-quart capacity', 99.99, 65, 'Appliances'),
('Instant Pot', 'Instant Pot pressure cooker 6-quart', 79.99, 85, 'Appliances'),
('Office Chair', 'Ergonomic office chair with lumbar support', 299.99, 40, 'Furniture'),
('Standing Desk', 'Height-adjustable standing desk', 399.99, 25, 'Furniture'),
('Bookshelf', '5-tier wooden bookshelf', 149.99, 35, 'Furniture');

-- Insert more users to reach 100
INSERT INTO users (username, email, password, first_name, last_name) VALUES
('user6', 'user6@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Six'),
('user7', 'user7@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Seven'),
('user8', 'user8@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Eight'),
('user9', 'user9@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Nine'),
('user10', 'user10@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBWNOx0wYaLHbG', 'User', 'Ten');

-- Insert more products to reach 100
INSERT INTO products (name, description, price, stock_quantity, category) VALUES
('T-Shirt', 'Cotton t-shirt in various colors', 19.99, 300, 'Clothing'),
('Jeans', 'Classic blue jeans', 59.99, 150, 'Clothing'),
('Sneakers', 'Casual sneakers for everyday wear', 79.99, 120, 'Clothing'),
('Jacket', 'Waterproof outdoor jacket', 149.99, 80, 'Clothing'),
('Watch', 'Stainless steel wristwatch', 199.99, 60, 'Accessories'),
('Sunglasses', 'UV protection sunglasses', 89.99, 100, 'Accessories'),
('Backpack', 'Laptop backpack with multiple compartments', 69.99, 90, 'Accessories'),
('Water Bottle', 'Insulated stainless steel water bottle', 24.99, 200, 'Sports'),
('Protein Powder', 'Whey protein powder 2lb container', 39.99, 150, 'Health'),
('Vitamins', 'Daily multivitamin supplement', 29.99, 180, 'Health');

-- Insert sample orders
INSERT INTO orders (user_id, total_amount, status) VALUES
(1, 2749.98, 'COMPLETED'),
(2, 999.99, 'SHIPPED'),
(3, 439.98, 'PENDING'),
(4, 1299.99, 'COMPLETED'),
(5, 89.99, 'PROCESSING');

-- Insert order items
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 2499.99),
(1, 6, 1, 249.99),
(2, 2, 1, 999.99),
(3, 7, 1, 349.99),
(3, 12, 1, 89.99),
(4, 4, 1, 1299.99),
(5, 12, 1, 89.99);

-- Show final counts
SELECT 'Users' as table_name, COUNT(*) as record_count FROM users
UNION ALL
SELECT 'Products' as table_name, COUNT(*) as record_count FROM products
UNION ALL
SELECT 'Orders' as table_name, COUNT(*) as record_count FROM orders
UNION ALL
SELECT 'Order Items' as table_name, COUNT(*) as record_count FROM order_items;
