
CREATE DATABASE FoodDeliveryDB;
USE FoodDeliveryDB;

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    city VARCHAR(50),
    pincode VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Restaurant (
    restaurant_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_name VARCHAR(100) NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15) UNIQUE,
    address TEXT,
    city VARCHAR(50),
    opening_time TIME,
    closing_time TIME,
    status ENUM('Open','Closed') DEFAULT 'Open'
);

CREATE TABLE Category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE Food_Item (
    food_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT,
    category_id INT,
    food_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255),
    availability BOOLEAN DEFAULT TRUE,

    FOREIGN KEY (restaurant_id)
        REFERENCES Restaurant(restaurant_id),

    FOREIGN KEY (category_id)
        REFERENCES Category(category_id)
);

CREATE TABLE Cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    food_id INT,
    quantity INT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (customer_id)
        REFERENCES Customer(customer_id),

    FOREIGN KEY (food_id)
        REFERENCES Food_Item(food_id)
);


CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    restaurant_id INT,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    payment_status ENUM('Pending','Paid','Failed') DEFAULT 'Pending',
    order_status ENUM('Placed','Accepted','Preparing','Out for Delivery','Delivered','Cancelled')
    DEFAULT 'Placed',
    delivery_address TEXT,

    FOREIGN KEY (customer_id)
        REFERENCES Customer(customer_id),

    FOREIGN KEY (restaurant_id)
        REFERENCES Restaurant(restaurant_id)
);

CREATE TABLE Order_Details (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    food_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10,2),

    FOREIGN KEY (order_id)
        REFERENCES Orders(order_id),

    FOREIGN KEY (food_id)
        REFERENCES Food_Item(food_id)
);

CREATE TABLE Payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    payment_method ENUM('Cash','UPI','Card','Net Banking'),
    amount DECIMAL(10,2),
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    transaction_id VARCHAR(100) UNIQUE,
    payment_status ENUM('Success','Pending','Failed'),

    FOREIGN KEY (order_id)
        REFERENCES Orders(order_id)
);
CREATE TABLE Delivery_Partner (
    delivery_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100),
    phone VARCHAR(15) UNIQUE,
    email VARCHAR(100) UNIQUE,
    vehicle_number VARCHAR(20),
    vehicle_type VARCHAR(30),
    availability BOOLEAN DEFAULT TRUE
);

CREATE TABLE Delivery_Tracking (
    tracking_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    delivery_id INT,
    current_location VARCHAR(255),
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    estimated_time VARCHAR(30),
    tracking_status ENUM('Assigned','Picked Up','On the Way','Delivered'),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id)
        REFERENCES Orders(order_id),

    FOREIGN KEY (delivery_id)
        REFERENCES Delivery_Partner(delivery_id)
);

CREATE TABLE Review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    restaurant_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comments TEXT,
    review_date DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (customer_id)
        REFERENCES Customer(customer_id),

    FOREIGN KEY (restaurant_id)
        REFERENCES Restaurant(restaurant_id)
);


CREATE TABLE Admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE
);

INSERT INTO Category(category_name, description)
VALUES
('Pizza','Italian Pizza'),
('Burger','Fast Food'),
('Biryani','Rice Dishes'),
('Dessert','Sweet Items'),
('Beverages','Cold Drinks');

INSERT INTO Restaurant
(restaurant_name,owner_name,email,phone,address,city,opening_time,closing_time)
VALUES
('Food Paradise','Rahul',
'foodparadise@gmail.com',
'9876543210',
'Anna Nagar',
'Chennai',
'09:00:00',
'22:00:00');

INSERT INTO Customer
(full_name,email,phone,password,address,city,pincode)
VALUES
('Ajay Richard',
'ajay@gmail.com',
'9876543211',
'12345',
'No.10 Main Road',
'Chennai',
'600001');

INSERT INTO Food_Item
(restaurant_id,category_id,food_name,description,price)
VALUES
(1,1,'Margherita Pizza','Cheese Pizza',299.00),
(1,2,'Chicken Burger','Spicy Burger',180.00),
(1,3,'Chicken Biryani','Hyderabadi Style',250.00);

INSERT INTO Delivery_Partner
(full_name,phone,email,vehicle_number,vehicle_type)
VALUES
('Ramesh',
'9876543222',
'ramesh@gmail.com',
'TN01AB1234',
'Bike');

INSERT INTO Admin
(username,password,email)
VALUES
('admin',
'admin123',
'admin@gmail.com');
