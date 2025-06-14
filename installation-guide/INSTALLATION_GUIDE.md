# E-Commerce Application Installation Guide

## Table of Contents

1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [System Requirements](#system-requirements)
4. [Database Setup](#database-setup)
5. [Application Configuration](#application-configuration)
6. [Build and Compilation](#build-and-compilation)
7. [Running the Application](#running-the-application)
8. [Accessing the Application](#accessing-the-application)
9. [User Accounts](#user-accounts)
10. [Troubleshooting](#troubleshooting)
11. [Important Disclaimers](#important-disclaimers)

---

## Overview

This is a Spring Boot-based e-commerce application designed for educational purposes. The application demonstrates various web development concepts including MVC architecture, database integration, user authentication, shopping cart functionality, and order management.

**⚠️ IMPORTANT: This application is for learning purposes only and should NOT be used in production environments.**

---

## Prerequisites

### Required Software

1. **Java Development Kit (JDK)**
   - Version: JDK 8 or higher
   - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

2. **Apache Maven**
   - Version: 3.6.0 or higher
   - Download: [Apache Maven](https://maven.apache.org/download.cgi)

3. **MySQL Database Server**
   - Version: 8.0 or higher
   - Download: [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

4. **Git** (Optional, for version control)
   - Download: [Git](https://git-scm.com/downloads)

### Environment Setup

1. **Set JAVA_HOME environment variable**
   ```bash
   # Windows
   set JAVA_HOME=C:\Program Files\Java\jdk-1.8.x
   
   # Linux/Mac
   export JAVA_HOME=/usr/lib/jvm/java-8-openjdk
   ```

2. **Add Maven to PATH**
   ```bash
   # Windows
   set PATH=%PATH%;C:\apache-maven-3.x.x\bin
   
   # Linux/Mac
   export PATH=$PATH:/opt/apache-maven-3.x.x/bin
   ```

3. **Verify installations**
   ```bash
   java -version
   mvn -version
   mysql --version
   ```

---

## System Requirements

### Minimum Hardware Requirements
- **RAM**: 4 GB minimum, 8 GB recommended
- **Storage**: 2 GB free space
- **CPU**: Dual-core processor

### Operating System Support
- Windows 10/11
- macOS 10.14+
- Linux (Ubuntu 18.04+, CentOS 7+)

### Network Requirements
- Internet connection for downloading dependencies
- Port 8080 available for the application
- Port 3306 available for MySQL

---

## Database Setup

### 1. Install and Start MySQL

**Windows:**
```cmd
# Start MySQL service
net start mysql
```

**Linux:**
```bash
# Start MySQL service
sudo systemctl start mysql
sudo systemctl enable mysql
```

**macOS:**
```bash
# Start MySQL service
brew services start mysql
```

### 2. Create Database and User

Connect to MySQL as root:
```sql
mysql -u root -p
```

Execute the following SQL commands:

```sql
-- Create database
CREATE DATABASE ecommerce_db;

-- Create user (optional, can use root)
CREATE USER 'ecommerce_user'@'localhost' IDENTIFIED BY 'Password123!';

-- Grant privileges
GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecommerce_user'@'localhost';
FLUSH PRIVILEGES;

-- Verify database creation
SHOW DATABASES;

-- Use the database
USE ecommerce_db;
```

### 3. Database Schema

The application uses Hibernate with `spring.jpa.hibernate.ddl-auto=update` configuration, which means:
- Tables will be created automatically on first run
- Sample data will be populated via `DataInitializer.java`
- No manual schema creation required

### 4. Sample Data

The application automatically creates:
- **40 products** across 6 categories (Electronics, Clothing, Books, Home, Sports, Beauty)
- **2 user accounts** (customer and retailer)
- **Product images** using real photos from Pexels

---

## Application Configuration

### 1. Download/Clone the Application

If using Git:
```bash
git clone <repository-url>
cd ecommerce-app
```

### 2. Configuration Files

The main configuration is in `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Password123!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080

# Thymeleaf Configuration
spring.thymeleaf.cache=false

# Management Endpoints
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

### 3. Update Database Credentials

If you're using different database credentials, update the following in `application.properties`:

#### Basic Credential Update:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### Complete Connection String Examples:

**For Different Database Name:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

**For Different MySQL Port:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

**For Remote MySQL Server:**
```properties
spring.datasource.url=jdbc:mysql://192.168.1.100:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=remote_user
spring.datasource.password=remote_password
```

**For MySQL with SSL (Production-like):**
```properties
spring.datasource.url=jdbc:mysql://your-server.com:3306/ecommerce_db?useSSL=true&requireSSL=true&serverTimezone=UTC
spring.datasource.username=secure_user
spring.datasource.password=secure_password
```

**For Custom User Created by database-setup.sql:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=ecommerce_user
spring.datasource.password=Password123!
```

#### Connection String Parameters Explained:

| Parameter | Purpose | Example Values |
|-----------|---------|----------------|
| `useSSL` | Enable/disable SSL | `true`, `false` |
| `allowPublicKeyRetrieval` | Allow public key retrieval | `true`, `false` |
| `serverTimezone` | Set timezone | `UTC`, `America/New_York` |
| `requireSSL` | Force SSL connection | `true`, `false` |
| `autoReconnect` | Auto reconnect on failure | `true`, `false` |

#### Steps to Update Connection String:

1. **Open** the configuration file:
   ```
   src/main/resources/application.properties
   ```

2. **Locate** the database configuration section:
   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=Password123!
   ```

3. **Update** the values according to your MySQL setup

4. **Save** the file

5. **Restart** the application:
   ```bash
   mvn spring-boot:run
   ```

#### Common Connection String Scenarios:

**Scenario 1: XAMPP/WAMP Users**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
```
*Note: XAMPP often has empty root password*

**Scenario 2: Docker MySQL Container**
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=mysql_user
spring.datasource.password=mysql_password
```

**Scenario 3: Cloud Database (AWS RDS, etc.)**
```properties
spring.datasource.url=jdbc:mysql://your-rds-endpoint.amazonaws.com:3306/ecommerce_db?useSSL=true&serverTimezone=UTC
spring.datasource.username=admin
spring.datasource.password=your_cloud_password
```

---

## Build and Compilation

### 1. Navigate to Project Directory
```bash
cd /path/to/ecommerce-app
```

### 2. Clean and Compile
```bash
# Clean previous builds
mvn clean

# Compile the application
mvn compile

# Run tests (optional)
mvn test

# Package the application
mvn package
```

### 3. Verify Build Success
Look for the message:
```
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXX s
```

### 4. Generated Artifacts
After successful build, you'll find:
- `target/classes/` - Compiled classes
- `target/ecommerce-app-1.0.0.jar` - Executable JAR file

---

## Running the Application

### Method 1: Using Maven (Recommended for Development)
```bash
mvn spring-boot:run
```

### Method 2: Using JAR File
```bash
java -jar target/ecommerce-app-1.0.0.jar
```

### Method 3: Using IDE
1. Import project into your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Run the main class: `com.example.ecommerce.EcommerceApplication`

### 4. Verify Application Startup

Look for these log messages:
```
Started EcommerceApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

---

## Accessing the Application

### Application URLs

| Page | URL | Description |
|------|-----|-------------|
| **Home Page** | `http://localhost:8080/` | Main landing page with featured products |
| **Products** | `http://localhost:8080/products` | Complete product catalog |
| **Login** | `http://localhost:8080/login` | User authentication |
| **Register** | `http://localhost:8080/register` | New user registration |
| **Shopping Cart** | `http://localhost:8080/cart` | Cart management |
| **Checkout** | `http://localhost:8080/checkout` | Order placement |
| **Orders** | `http://localhost:8080/orders` | Order history (requires login) |
| **Retailer Dashboard** | `http://localhost:8080/retailer/dashboard` | Admin panel (retailer login required) |

### Network Access

If accessing from other machines on the network:
```
http://YOUR_IP_ADDRESS:8080/
```

Replace `YOUR_IP_ADDRESS` with your machine's IP address.

---

## User Accounts

### Pre-configured Accounts

The application comes with two pre-configured user accounts:

#### Customer Account
- **Username**: `customer@example.com`
- **Password**: `password123`
- **Role**: Customer
- **Permissions**: Browse products, add to cart, place orders

#### Retailer Account
- **Username**: `retailer@example.com`
- **Password**: `password123`
- **Role**: Retailer
- **Permissions**: All customer permissions + product management, dashboard access

### Creating New Accounts

1. Visit: `http://localhost:8080/register`
2. Fill in the registration form
3. New accounts are created with Customer role by default

---

## Troubleshooting

### Common Issues and Solutions

#### 1. Port 8080 Already in Use
**Error**: `Port 8080 was already in use`

**Solution**:
```bash
# Find process using port 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Linux/Mac

# Kill the process or change port in application.properties
server.port=8081
```

#### 2. Database Connection Failed
**Error**: `Communications link failure`

**Solutions**:
- Verify MySQL is running: `systemctl status mysql`
- Check database credentials in `application.properties`
- Ensure database `ecommerce_db` exists
- Verify firewall settings

#### 3. Maven Build Failures
**Error**: `Failed to execute goal`

**Solutions**:
```bash
# Clear Maven cache
mvn dependency:purge-local-repository

# Update dependencies
mvn clean install -U

# Skip tests if failing
mvn clean install -DskipTests
```

#### 4. Java Version Issues
**Error**: `UnsupportedClassVersionError`

**Solution**:
- Ensure JDK 8+ is installed
- Verify JAVA_HOME points to correct JDK
- Check Maven is using correct Java version: `mvn -version`

#### 5. Memory Issues
**Error**: `OutOfMemoryError`

**Solution**:
```bash
# Increase JVM memory
export MAVEN_OPTS="-Xmx2048m -Xms1024m"
mvn spring-boot:run
```

### Log Files

Application logs are displayed in the console. For file logging, add to `application.properties`:
```properties
logging.file.name=application.log
logging.level.com.example.ecommerce=DEBUG
```

### Health Check

Verify application health:
```bash
curl http://localhost:8080/actuator/health
```

Expected response:
```json
{"status":"UP"}
```

---

## Important Disclaimers

### ⚠️ Educational Purpose Only

**This application is designed for learning and educational purposes only. It should NOT be used in production environments.**

### Learning Objectives

This application demonstrates:
- Spring Boot framework usage
- MVC architecture implementation
- Database integration with JPA/Hibernate
- User authentication and authorization
- Shopping cart functionality
- Order management system
- Template engine usage (Thymeleaf)
- RESTful API design principles

---

## Support and Resources

### Documentation
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)

### Community Resources
- [Spring Boot GitHub](https://github.com/spring-projects/spring-boot)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/spring-boot)
- [Spring Community](https://spring.io/community)

---

**Document Version**: 1.0  
**Last Updated**: June 2025  
**Application Version**: 1.0.0

---

*This installation guide provides comprehensive instructions for setting up and running the e-commerce application. For additional support or questions about the educational content, please refer to the course materials or instructor guidance.*
