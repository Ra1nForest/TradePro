# 📦 TradePro - Merchant Inventory & Transaction System

> A comprehensive inventory and order management system designed for individual merchants, featuring a hybrid customer management model and strict order lifecycle control.

## 📖 Introduction
**TradePro** is a full-stack web application designed to help individual merchants streamline their daily operations. Unlike traditional e-commerce platforms, TradePro focuses on **flexible inventory tracking** (Location, Price, Discount) and handles a unique **Hybrid Customer Architecture** that supports both platform-registered users and unregistered walk-in clients.

This project demonstrates a complete **Software Development Life Cycle (SDLC)**, from database design to RESTful API implementation and frontend integration.

## 🛠️ Tech Stack

- **Frontend:** Vue.js, Axios, Element UI
- **Backend:** Java, Spring Boot, MyBatis
- **Database:** MySQL
- **Testing & QA:** Postman (API Testing), JUnit (Unit Testing)
- **Tools:** Git, Maven

## ✨ Key Features

### 1. 🏭 Advanced Inventory Management
- **Multi-Attribute Tracking:** Manages stock not just by quantity, but by **Storage Location**, **Base Price**, and **Dynamic Discount** rules.
- **Stock Safety:** Implements optimistic locking to prevent overselling during concurrent transactions.

### 2. 🔄 Robust Order Lifecycle (State Machine)
- Implemented a strict state transition logic for orders to ensure data consistency:
  - `CREATED` -> `SHIPPED` -> `DELIVERED` -> `COMPLETED`.
- Prevents invalid operations (e.g., trying to complete an order before it is shipped).

### 3. 👥 Hybrid Customer System (Core Highlight)
- **Registered Traders:** Supports "Friendship" mechanisms for verified platform users.
- **Unregistered Clients:** Allows merchants to tag walk-in customers as metadata (Order Remarks) without polluting the main user database.
- **Data Integrity:** Ensures foreign key constraints are handled correctly for both user types.

## ⚙️ Architecture & Testing

- **RESTful API Design:**
  - Follows strict HTTP status code standards (200, 400, 404, 500).
  - Standardized JSON response format: `{ code: 200, msg: "success", data: ... }`.
  
- **Data Validation:**
  - Backend validation using **Java Bean Validation (Hibernate Validator)** to intercept invalid inputs (e.g., negative price, empty stock) before they reach the database.

- **API Testing:**
  - Verified all endpoints using **Postman**, ensuring that the inventory deduction logic triggers correctly only upon order completion.

## 🚀 How to Run

### Backend (Spring Boot)
```bash
git clone https://github.com/Ra1nForest/TradePro.git
cd backend
mvn clean install
mvn spring-boot:run
