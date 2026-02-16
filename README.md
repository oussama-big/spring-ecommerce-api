# Store API - E-Commerce Backend
This project is a robust, production-ready e-commerce backend built with Spring Boot 3. It handles product catalogs, shopping carts, and secure payment processing via Stripe.


## 📍 Table of Contents

* [🚀 Key Features](#-key-features)
* [🛠️ Tech Stack](#️-tech-stack)
* [⚙️ Configuration & Setup](#️-configuration--setup)
    * [1. Prerequisites](#1-prerequisites)
    * [2. Environment Variables](#2-environment-variables)
    * [3. Installation](#3-installation)
* [📡 API Endpoints](#-api-endpoints)
* [📂 Project Structure](#-project-structure)
* [🛡️ Security Best Practices](#️-security-best-practices)


## 🚀 Key Features

Order Lifecycle Management: Complete flow from order creation to payment confirmation.

Stripe Integration: Supports Stripe Checkout and real-time Webhooks for secure transaction handling.

Pro Security: Stateless authentication using JWT (JSON Web Tokens).
RBAC (Role-Based Access Control): Granular access control for Users and Administrators.
Clean Architecture: Separation of concerns using Controller-Service-Repository patterns and DTOs.
Data Persistence: MySQL integration with JPA/Hibernate for reliable data storage.

## 🛠️ Tech Stack

Framework: Spring Boot 3.4
Language: Java 21
Database: MySQL 8
Security: Spring Security & JWT
Mapping: MapStruct & Lombok
Payment: Stripe Java SDK
Configuration: Dotenv (Externalized secrets management)


## ⚙️ Configuration & Setup

## 1. Prerequisites
Java 21 or higher
MySQL Server
Stripe Developer Account (Test Mode)

## 2. Environment Variables
To keep your secrets safe, this project uses a .env file. Create one in the root directory and add the following:

```
# JWT Configuration
JWT_SECRET=your_32_character_long_secret_key

# Stripe Configuration
STRIPE_SECRET_KEY=sk_test_your_secret_key
STRIPE_WEBHOOK_SECRET_KEY=whsec_your_webhook_key

# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/store_db
DB_USERNAME=root
DB_PASSWORD=your_mysql_password

```
## 3. Installation
```
# Clean and compile (this generates MapStruct mappers)
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```
# 📡 API Endpoints

Method,Endpoint,Description,Access
POST,/api/auth/login  --> Authenticate and receive JWT--->Public
POST,/api/orders/checkout ---> Create a Stripe Checkout Session--->User / Admin
POST,/api/webhooks/stripe --> Handle Stripe payment events,Stripe Webhook
GET,/api/admin/orders --> List all orders,Admin Only


# 📂 Project Structure

```
src/main/java/com/codewithmosh/store/
├── config/       # Security, Stripe, and JWT configurations
├── carts/        # Cart domain, DTOs, and Mappers
├── payments/     # Order management, Payment Gateways, and Webhooks
├── auth/         # Security filters and Authentication logic
└── common/       # Global exceptions and shared utilities
```

# 🛡️ Security Best Practices

Idempotency: Webhooks verify if an order is already processed to prevent duplicate payments.

Secrets Protection: The .env file is included in .gitignore to prevent leaking sensitive keys on GitHub.

Password Hashing: All user passwords are encrypted using BCrypt before being stored in MySQL.


