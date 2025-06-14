# 🛒 E-Commerce Application

A comprehensive Spring Boot-based e-commerce application designed for educational purposes.

## ⚠️ Important Disclaimer

**This application is for learning purposes only and should NOT be used in production environments.**

## 🚀 Quick Start

### Prerequisites
- Java 8+
- Maven 3.6+
- MySQL 8.0+

### Setup & Run
```bash
# 1. Clone the repository
git clone <repository-url>
cd ecommerce-app

# 2. Setup database
mysql -u root -p < database-setup.sql

# 3. Build and run
mvn clean compile
mvn spring-boot:run
```

### Access Application
- **Home**: http://localhost:8080/
- **Products**: http://localhost:8080/products
- **Login**: http://localhost:8080/login

### Test Accounts
- **Customer**: customer@example.com / password123
- **Retailer**: retailer@example.com / password123

## 📚 Documentation

### 📁 Installation Guide
All installation and setup documentation is located in the `installation-guide/` folder:

- **[INSTALLATION_GUIDE.md](installation-guide/INSTALLATION_GUIDE.md)** - Complete installation instructions
- **[INSTALLATION_GUIDE.html](installation-guide/INSTALLATION_GUIDE.html)** - Word-compatible formatted guide
- **[DATABASE_CONFIGURATION_GUIDE.md](installation-guide/DATABASE_CONFIGURATION_GUIDE.md)** - Database setup reference
- **[database-setup.sql](installation-guide/database-setup.sql)** - Database creation script
- **[setup.sh](installation-guide/setup.sh)** - Automated Linux/Mac setup
- **[setup.bat](installation-guide/setup.bat)** - Automated Windows setup

### 🚀 Quick Setup
```bash
# Automated setup (recommended)
cd installation-guide
./setup.sh          # Linux/Mac
setup.bat           # Windows

# Manual setup
mysql -u root -p < installation-guide/database-setup.sql
mvn clean compile
mvn spring-boot:run
```

For detailed installation instructions, see [installation-guide/INSTALLATION_GUIDE.md](installation-guide/INSTALLATION_GUIDE.md)

## 🎯 Features

- User Authentication & Authorization
- Product Catalog Management
- Shopping Cart Functionality
- Order Management System
- Retailer Dashboard
- Real Product Images
- Responsive Design
- Professional Features

## 🛠️ Technology Stack

- **Backend**: Spring Boot, Spring Data JPA, Spring Security
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **Database**: MySQL
- **Build Tool**: Maven
- **Java Version**: 8+

## 📁 Project Structure

```
ecommerce-app/
├── src/main/java/com/example/ecommerce/
│   ├── controller/          # Web controllers
│   ├── model/              # Entity classes
│   ├── repository/         # Data access layer
│   ├── service/            # Business logic
│   └── config/             # Configuration classes
├── src/main/resources/
│   ├── templates/          # Thymeleaf templates
│   ├── static/             # CSS, JS, images
│   └── application.properties
├── database-setup.sql      # Database setup script
├── INSTALLATION_GUIDE.md   # Detailed installation guide
└── README.md              # This file
```

## 🔧 Configuration

Key configuration in `application.properties`:
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=Password123!

# Server
server.port=8080

# JPA
spring.jpa.hibernate.ddl-auto=update
```

### 🔗 Update Database Connection

To use different database settings, modify `src/main/resources/application.properties`:

**Different Database:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

**Different Credentials:**
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**Remote MySQL:**
```properties
spring.datasource.url=jdbc:mysql://your-server.com:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=remote_user
spring.datasource.password=remote_password
```

## 🎓 Learning Objectives

This application demonstrates:
- MVC Architecture
- Database Integration
- User Authentication
- Shopping Cart Logic
- Order Processing
- Template Engines
- RESTful Design
- Security Considerations

## 🐛 Known Issues (Educational)

## 📞 Support

For detailed setup instructions and troubleshooting, refer to the [Installation Guide](installation-guide/INSTALLATION_GUIDE.md).

---

**Version**: 1.0.0  
**Last Updated**: June 2025  
**License**: Educational Use Only
