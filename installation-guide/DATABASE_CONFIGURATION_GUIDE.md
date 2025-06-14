# 🗃️ Database Configuration Guide

## Quick Reference for Updating MySQL Connection Strings

### 📍 Configuration File Location
```
src/main/resources/application.properties
```

---

## 🔧 Connection String Examples

### 1. **Default Configuration (Current)**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Password123!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 2. **Different Database Name**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Password123!
```

### 3. **Different MySQL Port**
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Password123!
```

### 4. **Custom User (from database-setup.sql)**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=ecommerce_user
spring.datasource.password=Password123!
```

### 5. **Remote MySQL Server**
```properties
spring.datasource.url=jdbc:mysql://192.168.1.100:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=remote_user
spring.datasource.password=remote_password
```

### 6. **XAMPP/WAMP (Empty Password)**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
```

### 7. **Docker MySQL Container**
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=mysql_user
spring.datasource.password=mysql_password
```

### 8. **Cloud Database (AWS RDS, Google Cloud SQL)**
```properties
spring.datasource.url=jdbc:mysql://your-rds-endpoint.amazonaws.com:3306/ecommerce_db?useSSL=true&requireSSL=true&serverTimezone=UTC
spring.datasource.username=admin
spring.datasource.password=your_cloud_password
```

### 9. **SSL Enabled (Production-like)**
```properties
spring.datasource.url=jdbc:mysql://your-server.com:3306/ecommerce_db?useSSL=true&requireSSL=true&verifyServerCertificate=false&serverTimezone=UTC
spring.datasource.username=secure_user
spring.datasource.password=secure_password
```

---

## 🔍 Connection String Parameters

| Parameter | Description | Common Values | Purpose |
|-----------|-------------|---------------|---------|
| `useSSL` | Enable SSL encryption | `true`, `false` | Security |
| `allowPublicKeyRetrieval` | Allow public key retrieval | `true`, `false` | Authentication |
| `serverTimezone` | Database timezone | `UTC`, `America/New_York` | Time handling |
| `requireSSL` | Force SSL connection | `true`, `false` | Security |
| `verifyServerCertificate` | Verify SSL certificate | `true`, `false` | Security |
| `autoReconnect` | Auto reconnect on failure | `true`, `false` | Reliability |
| `characterEncoding` | Character encoding | `utf8`, `utf8mb4` | Text handling |
| `useUnicode` | Enable Unicode support | `true`, `false` | Text handling |

---

## 📋 Step-by-Step Update Process

### Step 1: Open Configuration File
```bash
# Navigate to project directory
cd /path/to/ecommerce-app

# Open configuration file
nano src/main/resources/application.properties
# or use your preferred editor
```

### Step 2: Locate Database Section
Look for this section in the file:
```properties
# Database Configuration - Current Setup
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Password123!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Step 3: Update Values
Replace the values according to your MySQL setup:
- **URL**: Change host, port, or database name
- **Username**: Change to your MySQL username
- **Password**: Change to your MySQL password

### Step 4: Save and Restart
```bash
# Save the file (Ctrl+S in most editors)

# Restart the application
mvn spring-boot:run
```

---

## 🚨 Common Scenarios & Solutions

### Scenario 1: "Access denied for user 'root'@'localhost'"
**Solution**: Check username and password
```properties
spring.datasource.username=your_correct_username
spring.datasource.password=your_correct_password
```

### Scenario 2: "Unknown database 'ecommerce_db'"
**Solution**: Create database first
```sql
mysql -u root -p
CREATE DATABASE ecommerce_db;
```

### Scenario 3: "Communications link failure"
**Solution**: Check if MySQL is running and port is correct
```bash
# Check MySQL status
sudo systemctl status mysql

# Check if port 3306 is open
netstat -an | grep 3306
```

### Scenario 4: "Connection refused"
**Solution**: Verify MySQL server is running and accessible
```bash
# Start MySQL service
sudo systemctl start mysql

# Test connection
mysql -u root -p -h localhost
```

---

## 🧪 Testing Connection

### Method 1: Application Startup
```bash
mvn spring-boot:run
```
Look for successful startup messages without database errors.

### Method 2: Health Check
```bash
curl http://localhost:8080/actuator/health
```
Should return: `{"status":"UP"}`

### Method 3: Direct MySQL Test
```bash
mysql -u your_username -p -h your_host -P your_port your_database
```

---

## 📞 Quick Help

### If you need to:
- **Change database name**: Update the database name in the URL
- **Use different credentials**: Update username and password
- **Connect to remote server**: Change localhost to server IP/hostname
- **Use different port**: Change 3306 to your port number
- **Enable SSL**: Change `useSSL=false` to `useSSL=true`

### Remember to:
1. ✅ Save the configuration file
2. ✅ Restart the application
3. ✅ Check application logs for errors
4. ✅ Test the connection

---

**This guide covers all common database configuration scenarios for the e-commerce application.**
