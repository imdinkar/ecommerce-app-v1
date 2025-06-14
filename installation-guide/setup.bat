@echo off
REM =====================================================
REM E-Commerce Application Setup Script for Windows
REM =====================================================

echo.
echo 🛒 E-Commerce Application Setup
echo ================================
echo.

REM Navigate to project root directory
cd /d "%~dp0\.."

REM Check Java
echo Checking prerequisites...
echo.

java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java not found. Please install JDK 11 or higher.
    pause
    exit /b 1
) else (
    echo ✅ Java found
)

REM Check Maven
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven not found. Please install Apache Maven 3.6+.
    pause
    exit /b 1
) else (
    echo ✅ Maven found
)

REM Check MySQL
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  MySQL client not found. Please ensure MySQL is installed.
) else (
    echo ✅ MySQL client found
)

echo.
echo ℹ️  All prerequisites check completed!
echo.

REM Database setup
echo Setting up database...
echo ⚠️  Please ensure MySQL server is running before proceeding.
echo.

set /p setup_db="Do you want to setup the database now? (y/n): "
if /i "%setup_db%"=="y" (
    echo ℹ️  Setting up database...
    mysql -u root -p < installation-guide\database-setup.sql
    if %errorlevel% equ 0 (
        echo ✅ Database setup completed successfully!
    ) else (
        echo ❌ Database setup failed. Please check your MySQL connection.
        pause
        exit /b 1
    )
) else (
    echo ⚠️  Skipping database setup. Please run 'mysql -u root -p < installation-guide\database-setup.sql' manually.
)

echo.

REM Build application
echo Building application...
echo ℹ️  Cleaning previous builds...
call mvn clean

echo ℹ️  Compiling application...
call mvn compile

if %errorlevel% neq 0 (
    echo ❌ Compilation failed. Please check the error messages above.
    pause
    exit /b 1
) else (
    echo ✅ Application compiled successfully!
)

echo.

REM Ask if user wants to run the application
set /p run_app="Do you want to start the application now? (y/n): "
if /i "%run_app%"=="y" (
    echo ℹ️  Starting application...
    echo.
    echo ✅ Application will be available at: http://localhost:8080/
    echo ℹ️  Test accounts:
    echo   Customer: customer@example.com / password123
    echo   Retailer: retailer@example.com / password123
    echo.
    echo ⚠️  Press Ctrl+C to stop the application
    echo.
    
    REM Start the application
    call mvn spring-boot:run
) else (
    echo.
    echo ✅ Setup completed successfully!
    echo ℹ️  To start the application later, run: mvn spring-boot:run
    echo ℹ️  Application will be available at: http://localhost:8080/
    echo.
    echo ℹ️  Test accounts:
    echo   Customer: customer@example.com / password123
    echo   Retailer: retailer@example.com / password123
)

echo.
echo ⚠️  Remember: This application is for educational purposes only!
echo.
pause
