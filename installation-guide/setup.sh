#!/bin/bash

# =====================================================
# E-Commerce Application Setup Script
# =====================================================

echo "🛒 E-Commerce Application Setup"
echo "================================"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

# Navigate to project root directory
cd "$(dirname "$0")/.."

# Check prerequisites
echo "Checking prerequisites..."
echo ""

# Check Java
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    print_status "Java found: $JAVA_VERSION"
else
    print_error "Java not found. Please install JDK 11 or higher."
    exit 1
fi

# Check Maven
if command -v mvn &> /dev/null; then
    MAVEN_VERSION=$(mvn -version 2>&1 | head -n 1 | cut -d' ' -f3)
    print_status "Maven found: $MAVEN_VERSION"
else
    print_error "Maven not found. Please install Apache Maven 3.6+."
    exit 1
fi

# Check MySQL
if command -v mysql &> /dev/null; then
    print_status "MySQL client found"
else
    print_warning "MySQL client not found. Please ensure MySQL is installed."
fi

echo ""
print_info "All prerequisites check completed!"
echo ""

# Database setup
echo "Setting up database..."
print_warning "Please ensure MySQL server is running before proceeding."
echo ""

read -p "Do you want to setup the database now? (y/n): " -n 1 -r
echo ""
if [[ $REPLY =~ ^[Yy]$ ]]; then
    print_info "Setting up database..."
    mysql -u root -p < installation-guide/database-setup.sql
    if [ $? -eq 0 ]; then
        print_status "Database setup completed successfully!"
    else
        print_error "Database setup failed. Please check your MySQL connection."
        exit 1
    fi
else
    print_warning "Skipping database setup. Please run 'mysql -u root -p < installation-guide/database-setup.sql' manually."
fi

echo ""

# Build application
echo "Building application..."
print_info "Cleaning previous builds..."
mvn clean

print_info "Compiling application..."
mvn compile

if [ $? -eq 0 ]; then
    print_status "Application compiled successfully!"
else
    print_error "Compilation failed. Please check the error messages above."
    exit 1
fi

echo ""

# Ask if user wants to run the application
read -p "Do you want to start the application now? (y/n): " -n 1 -r
echo ""
if [[ $REPLY =~ ^[Yy]$ ]]; then
    print_info "Starting application..."
    echo ""
    print_status "Application will be available at: http://localhost:8080/"
    print_info "Test accounts:"
    echo "  Customer: customer@example.com / password123"
    echo "  Retailer: retailer@example.com / password123"
    echo ""
    print_warning "Press Ctrl+C to stop the application"
    echo ""
    
    # Start the application
    mvn spring-boot:run
else
    echo ""
    print_status "Setup completed successfully!"
    print_info "To start the application later, run: mvn spring-boot:run"
    print_info "Application will be available at: http://localhost:8080/"
    echo ""
    print_info "Test accounts:"
    echo "  Customer: customer@example.com / password123"
    echo "  Retailer: retailer@example.com / password123"
fi

echo ""
print_warning "Remember: This application is for educational purposes only!"
echo ""
