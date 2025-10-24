# 📦 Inventory Management System API

A backend API built with **Java Spring Boot** to manage products in a warehouse.  
It provides endpoints for **CRUD operations on products**, **stock management**, and additional features like **low-stock monitoring**.

---

## 🚀 Features

- **Product Management**
    - Create, Read, Update, Delete products
    - Each product has: `id`, `name`, `description`, `stockQuantity`, `lowStockThreshold`

- **Inventory Logic**
    - Increase stock quantity
    - Decrease stock quantity (with validation so stock never goes below 0)
    - Low stock monitoring (`/api/products/low-stock`)

- **Validation & Error Handling**
    - Global exception handling
    - Proper HTTP status codes (`400`, `404`, `409`, etc.)

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Maven

---

## ⚙️ Setup & Run Locally

### 1. Clone the repository
```bash
git clone https://github.com/divyanshu-293/inventory-management-api.git
cd inventory-management-api
