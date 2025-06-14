# 📚 Installation Guide Documentation

Welcome to the comprehensive installation guide for the E-Commerce Application!

## 📋 Documentation Files

### 📖 Main Installation Guides
- **[INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md)** - Complete step-by-step installation guide (Markdown format)
- **[INSTALLATION_GUIDE.html](INSTALLATION_GUIDE.html)** - Professional formatted guide (Word-compatible)

### 🗃️ Database Setup
- **[DATABASE_CONFIGURATION_GUIDE.md](DATABASE_CONFIGURATION_GUIDE.md)** - Comprehensive database configuration reference
- **[database-setup.sql](database-setup.sql)** - Automated database setup script

### 🔧 Automated Setup Scripts
- **[setup.sh](setup.sh)** - Automated setup for Linux/Mac systems
- **[setup.bat](setup.bat)** - Automated setup for Windows systems

### 📄 Documentation Overview
- **[DOCUMENTATION_SUMMARY.md](DOCUMENTATION_SUMMARY.md)** - Overview of all documentation files

---

## 🚀 Quick Start Options

### Option 1: Automated Setup (Recommended)
```bash
# Linux/Mac
./setup.sh

# Windows
setup.bat
```

### Option 2: Manual Database + Maven
```bash
# Setup database
mysql -u root -p < database-setup.sql

# Build and run
mvn clean compile
mvn spring-boot:run
```

### Option 3: Follow Detailed Guide
Open [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) or [INSTALLATION_GUIDE.html](INSTALLATION_GUIDE.html) for complete instructions.

---

## 🎯 Choose Your Documentation

| Need | Recommended File |
|------|------------------|
| **Quick Setup** | [setup.sh](setup.sh) or [setup.bat](setup.bat) |
| **Complete Instructions** | [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) |
| **Word Document** | [INSTALLATION_GUIDE.html](INSTALLATION_GUIDE.html) |
| **Database Help** | [DATABASE_CONFIGURATION_GUIDE.md](DATABASE_CONFIGURATION_GUIDE.md) |
| **Overview** | [DOCUMENTATION_SUMMARY.md](DOCUMENTATION_SUMMARY.md) |

---

## 📞 Quick Reference

### Application URLs (after setup)
- **Home**: http://localhost:8080/
- **Products**: http://localhost:8080/products
- **Login**: http://localhost:8080/login

### Test Accounts
- **Customer**: customer@example.com / password123
- **Retailer**: retailer@example.com / password123

### Prerequisites
- Java 11+
- Maven 3.6+
- MySQL 8.0+

---

**⚠️ Remember: This application is for educational purposes only!**
