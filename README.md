# Inventory Management System

## Introduction

This repository contains the implementation of an Inventory Management System for a grocery store. The system is designed to help store staff manage product inventory and process customer orders efficiently.

## Features

- **Manage Products**: Add, update, retrieve, and delete products.
- **Order Management**: Create and manage customer orders, including adding products to orders and calculating the total price.

## Domain Entities

### Product

Represents a single product in the store. Each product includes:
- **ID**: Auto-incremented identifier
- **UPC**: Unique product code
- **Name**: Name of the product
- **Price**: Price of the product
- **Inventory Count**: Quantity of the product in stock

### Order

Represents a customer order. Each order includes:
- **ID**: Auto-incremented identifier
- **Cashier ID**: ID of the cashier processing the order
- **Total Price**: Total price of the order
- **Items**: Map of product IDs to quantities ordered

## Endpoints

### Product Endpoints

- **Get Product by ID**
  - **URL**: `/api/products/{id}`
  - **Method**: GET
  - **URL Params**: `id=[long]` (Product ID)

- **Get Product by UPC**
  - **URL**: `/api/products/upc_number/{upc}`
  - **Method**: GET
  - **URL Params**: `upc=[long]` (UPC number)

- **Create Product**
  - **URL**: `/api/products/create`
  - **Method**: POST
  - **Body**: JSON representation of the product

- **Update Product**
  - **URL**: `/api/products/update`
  - **Method**: PUT
  - **Body**: JSON representation of the product

- **Delete Product**
  - **URL**: `/api/products/delete/{id}`
  - **Method**: DELETE
  - **URL Params**: `id=[long]` (Product ID)

### Order Endpoints

- **Get Order by ID**
  - **URL**: `/api/orders/{id}`
  - **Method**: GET
  - **URL Params**: `id=[long]` (Order ID)

- **Get Total Price of Order**
  - **URL**: `/api/orders/{id}/total_price`
  - **Method**: GET
  - **URL Params**: `id=[long]` (Order ID)

- **Create Order**
  - **URL**: `/api/orders/create`
  - **Method**: POST
  - **Body**: JSON representation of the order

- **Update Order**
  - **URL**: `/api/orders/update/{id}`
  - **Method**: PATCH
  - **URL Params**: `id=[long]` (Order ID)
  - **Body**: JSON representation of the patched order

- **Add Product to Order**
  - **URL**: `/api/orders/{orderId}/addProduct`
  - **Method**: PATCH
  - **URL Params**: `orderId=[long]` (Order ID)
  - **Query Params**: `upc=[long]` (UPC number), `quantity=[integer]` (Quantity)

- **Delete Order**
  - **URL**: `/api/orders/delete/{id}`
  - **Method**: DELETE
  - **URL Params**: `id=[long]` (Order ID)

## Database Schema

### Product Table

| Column            | Type         | Description                         |
|-------------------|--------------|-------------------------------------|
| `id`              | BIGINT       | Auto-incremented unique identifier   |
| `upc`             | BIGINT       | Unique product code                 |
| `name`            | VARCHAR(25)  | Name of the product                 |
| `price`           | DECIMAL(6,2) | Price of the product                |
| `inventory_count` | INT          | Quantity of the product in stock    |

**Example Data:**

| id | upc     | name  | price | inventory_count |
|----|---------|-------|-------|-----------------|
| 1  | 10000001| Apple | 1.20  | 100             |
| 2  | 10000002| Milk  | 5.00  | 50              |
| 3  | 10000003| Bagel | 3.50  | 75              |


### Orders Table

| Column         | Type         | Description                         |
|----------------|--------------|-------------------------------------|
| `id`           | BIGINT       | Auto-incremented unique identifier   |
| `cashier_id`   | BIGINT       | ID of the cashier processing the order|
| `total_price`  | DECIMAL(10,2)| Total price of the order            |

### Order Items Table

| Column      | Type         | Description                          |
|-------------|--------------|--------------------------------------|
| `order_id`  | BIGINT       | Foreign key to Orders table          |
| `product_id`| BIGINT       | Foreign key to Products table        |
| `quantity`  | INT          | Quantity of the product in the order |

## Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/mentezami/Inventory-Management.git
   ```
2. **Navigate to Project Directory:**

    ```bash
    cd Inventory-Management
    ```

3. **Build and Run the Application:** Follow the instructions in the project's `pom.xml` or `build.gradle` to build and run the application.

4. **Configure the Database:** Update your `application.properties` or `application.yml` with the appropriate database configurations.

## Usage

- Use REST clients like Postman or curl to interact with the API endpoints.
- Refer to the API documentation for detailed information on available endpoints and request formats.

## Contributing

Feel free to submit issues or pull requests if you have suggestions for improvements or bug fixes.

## License
