# Database Design – Food Delivery and Delivery Tracking Application

## 1. Database Overview

The **Food Delivery and Delivery Tracking Application** uses a relational database to store and manage customer information, restaurant details, food items, orders, payments, delivery tracking, reviews, and administrator data. The database is designed to minimize redundancy, maintain data integrity, and support efficient querying through primary and foreign key relationships.

---

# Database Tables

## 1. Customer Table

Stores customer registration and profile information.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| customer_id | INT | Primary Key, Auto Increment |
| full_name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | UNIQUE |
| phone | VARCHAR(15) | UNIQUE |
| password | VARCHAR(255) | NOT NULL |
| address | TEXT | NOT NULL |
| city | VARCHAR(50) | |
| pincode | VARCHAR(10) | |
| created_at | TIMESTAMP | Default Current Timestamp |

---

## 2. Restaurant Table

Stores restaurant information.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| restaurant_id | INT | Primary Key, Auto Increment |
| restaurant_name | VARCHAR(100) | NOT NULL |
| owner_name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | UNIQUE |
| phone | VARCHAR(15) | UNIQUE |
| address | TEXT | |
| city | VARCHAR(50) | |
| opening_time | TIME | |
| closing_time | TIME | |
| status | ENUM('Open','Closed') | Default 'Open' |

---

## 3. Category Table

Stores food categories.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| category_id | INT | Primary Key, Auto Increment |
| category_name | VARCHAR(50) | NOT NULL |
| description | TEXT | |

---

## 4. Food Item Table

Stores available food items.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| food_id | INT | Primary Key, Auto Increment |
| restaurant_id | INT | Foreign Key |
| category_id | INT | Foreign Key |
| food_name | VARCHAR(100) | NOT NULL |
| description | TEXT | |
| price | DECIMAL(10,2) | NOT NULL |
| image | VARCHAR(255) | |
| availability | BOOLEAN | Default TRUE |

---

## 5. Cart Table

Stores items added to the customer's cart.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| cart_id | INT | Primary Key, Auto Increment |
| customer_id | INT | Foreign Key |
| food_id | INT | Foreign Key |
| quantity | INT | NOT NULL |
| added_at | TIMESTAMP | Current Timestamp |

---

## 6. Orders Table

Stores order details.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| order_id | INT | Primary Key, Auto Increment |
| customer_id | INT | Foreign Key |
| restaurant_id | INT | Foreign Key |
| order_date | DATETIME | |
| total_amount | DECIMAL(10,2) | |
| payment_status | ENUM('Pending','Paid','Failed') | |
| order_status | ENUM('Placed','Accepted','Preparing','Out for Delivery','Delivered','Cancelled') | |
| delivery_address | TEXT | |

---

## 7. Order Details Table

Stores individual food items in each order.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| order_detail_id | INT | Primary Key, Auto Increment |
| order_id | INT | Foreign Key |
| food_id | INT | Foreign Key |
| quantity | INT | |
| price | DECIMAL(10,2) | |

---

## 8. Payment Table

Stores payment details.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| payment_id | INT | Primary Key, Auto Increment |
| order_id | INT | Foreign Key |
| payment_method | ENUM('Cash','UPI','Card','Net Banking') | |
| amount | DECIMAL(10,2) | |
| payment_date | DATETIME | |
| transaction_id | VARCHAR(100) | UNIQUE |
| payment_status | ENUM('Success','Pending','Failed') | |

---

## 9. Delivery Partner Table

Stores delivery executive information.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| delivery_id | INT | Primary Key, Auto Increment |
| full_name | VARCHAR(100) | |
| phone | VARCHAR(15) | UNIQUE |
| email | VARCHAR(100) | UNIQUE |
| vehicle_number | VARCHAR(20) | |
| vehicle_type | VARCHAR(30) | |
| availability | BOOLEAN | Default TRUE |

---

## 10. Delivery Tracking Table

Stores live delivery tracking information.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| tracking_id | INT | Primary Key, Auto Increment |
| order_id | INT | Foreign Key |
| delivery_id | INT | Foreign Key |
| current_location | VARCHAR(255) | |
| latitude | DECIMAL(10,7) | |
| longitude | DECIMAL(10,7) | |
| estimated_time | VARCHAR(30) | |
| tracking_status | ENUM('Assigned','Picked Up','On the Way','Delivered') | |
| last_updated | TIMESTAMP | Current Timestamp |

---

## 11. Review Table

Stores customer ratings and reviews.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| review_id | INT | Primary Key, Auto Increment |
| customer_id | INT | Foreign Key |
| restaurant_id | INT | Foreign Key |
| rating | INT | CHECK (rating BETWEEN 1 AND 5) |
| comments | TEXT | |
| review_date | DATETIME | |

---

## 12. Admin Table

Stores administrator login details.

| Column Name | Data Type | Constraints |
|-------------|-----------|------------|
| admin_id | INT | Primary Key, Auto Increment |
| username | VARCHAR(50) | UNIQUE |
| password | VARCHAR(255) | NOT NULL |
| email | VARCHAR(100) | UNIQUE |

---

# Entity Relationships

- One **Customer** can place **many Orders**.
- One **Restaurant** can have **many Food Items**.
- One **Category** can contain **many Food Items**.
- One **Order** can contain **many Order Details**.
- One **Food Item** can appear in **many Order Details**.
- One **Order** has **one Payment**.
- One **Delivery Partner** can deliver **many Orders**.
- One **Order** has **one Delivery Tracking** record.
- One **Customer** can write **many Reviews**.
- One **Restaurant** can receive **many Reviews**.
- One **Customer** can have **many Cart Items**.

---

# ER Diagram (Text Representation)

```text
CUSTOMER
---------
customer_id (PK)
full_name
email
phone
password
address
city
pincode

      |
      | 1
      |
      | M
ORDERS
---------
order_id (PK)
customer_id (FK)
restaurant_id (FK)
total_amount
order_status
payment_status

      |
      | 1
      |
      | M
ORDER_DETAILS
---------------
order_detail_id (PK)
order_id (FK)
food_id (FK)
quantity
price

FOOD_ITEM
-----------
food_id (PK)
restaurant_id (FK)
category_id (FK)
food_name
price

CATEGORY
----------
category_id (PK)
category_name

RESTAURANT
-------------
restaurant_id (PK)
restaurant_name
owner_name

PAYMENT
---------
payment_id (PK)
order_id (FK)

DELIVERY_PARTNER
------------------
delivery_id (PK)
name
phone

DELIVERY_TRACKING
-------------------
tracking_id (PK)
order_id (FK)
delivery_id (FK)

REVIEW
---------
review_id (PK)
customer_id (FK)
restaurant_id (FK)

ADMIN
--------
admin_id (PK)
username
password
```

---

# Normalization

The database follows **Third Normal Form (3NF)**:

- **1NF (First Normal Form):** Each table contains atomic values with no repeating groups.
- **2NF (Second Normal Form):** Non-key attributes are fully dependent on the primary key.
- **3NF (Third Normal Form):** No transitive dependencies; each non-key attribute depends only on the primary key.

---

# Advantages of the Database Design

- Reduces data redundancy.
- Maintains data integrity using primary and foreign keys.
- Supports secure user authentication.
- Enables efficient order and payment management.
- Provides real-time delivery tracking.
- Simplifies restaurant and menu management.
- Generates reports for business analytics.
- Easily scalable for future features such as coupons, notifications, GPS integration, and loyalty programs.