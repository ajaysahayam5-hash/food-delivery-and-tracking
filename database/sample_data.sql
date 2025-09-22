-- Sample data for food_delivery (H2 dev + Postgres/Railway compatible)
-- All demo passwords are 'password123' (valid BCrypt, cost 10)
-- Demo logins: admin@fooddelivery.com / ajay@gmail.com / owner@foodparadise.com / owner@spicevilla.com / ramesh@gmail.com (all password123)

-- Categories
INSERT INTO menu_categories (name, description, image_url) VALUES
('Pizza','Italian pizzas with cheese and toppings','https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400'),
('Burger','Juicy burgers with fresh ingredients','https://images.unsplash.com/photo-1568909344668-6f14a07b56a0?w=400'),
('Biryani','Aromatic Hyderabadi dum biryani','https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400'),
('South Indian','Dosa, idli, vada and filter coffee','https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400'),
('Chinese','Hakka noodles, fried rice, momos','https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400'),
('Dessert','Brownies, ice cream and cakes','https://images.unsplash.com/photo-1551024601-bec78aea704b?w=400'),
('Beverages','Cold coffee, shakes and fresh juice','https://images.unsplash.com/photo-1544145945-f90425340c7e?w=400'),
('Healthy','Salads, bowls and grilled plates','https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400');

-- Users (BCrypt for 'password123')
INSERT INTO users (full_name, email, phone, password, role, enabled, email_verified) VALUES
('Admin User','admin@fooddelivery.com','9000000001','$2b$10$hgcCJn65FaQc715.q7.YvetpHsJcADF6ZqS3x/ki3ShRn6UjYUOtG','ADMIN',true,true),
('Ajay Richard','ajay@gmail.com','9876543211','$2b$10$hgcCJn65FaQc715.q7.YvetpHsJcADF6ZqS3x/ki3ShRn6UjYUOtG','CUSTOMER',true,true),
('Rahul Owner','owner@foodparadise.com','9876543210','$2b$10$hgcCJn65FaQc715.q7.YvetpHsJcADF6ZqS3x/ki3ShRn6UjYUOtG','RESTAURANT',true,true),
('Priya Owner','owner@spicevilla.com','9876543215','$2b$10$hgcCJn65FaQc715.q7.YvetpHsJcADF6ZqS3x/ki3ShRn6UjYUOtG','RESTAURANT',true,true),
('Kumar Owner','owner@chennaidosa.com','9876543219','$2b$10$hgcCJn65FaQc715.q7.YvetpHsJcADF6ZqS3x/ki3ShRn6UjYUOtG','RESTAURANT',true,true),
('Ramesh Delivery','ramesh@gmail.com','9876543222','$2b$10$hgcCJn65FaQc715.q7.YvetpHsJcADF6ZqS3x/ki3ShRn6UjYUOtG','DELIVERY_PARTNER',true,true),
('Suresh Delivery','suresh@delivery.com','9876543223','$2b$10$hgcCJn65FaQc715.q7.YvetpHsJcADF6ZqS3x/ki3ShRn6UjYUOtG','DELIVERY_PARTNER',true,true);

-- Restaurants (owner_id references users)
INSERT INTO restaurants (owner_id, restaurant_name, description, address, city, pincode, phone, email, image_url, rating, delivery_time, price_for_two, status) VALUES
(3, 'Food Paradise','Authentic North and South Indian delights','Anna Nagar, Chennai','Chennai','600040','9876543210','foodparadise@gmail.com','https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=600',4.4,'30-40 min',400,'Open'),
(4, 'Spice Villa','Hyderabadi biryani and curries','T Nagar, Chennai','Chennai','600017','9876543216','spicevilla@gmail.com','https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=600',4.5,'25-35 min',350,'Open'),
(3, 'Burger Hub','Crispy burgers, fries and shakes','Velachery, Chennai','Chennai','600042','9876543217','burgerhub@gmail.com','https://images.unsplash.com/photo-1550547660-d9450f859349?w=600',4.2,'20-30 min',300,'Open'),
(4, 'Pizza Corner','Wood-fired pizzas and garlic bread','Adyar, Chennai','Chennai','600020','9876543218','pizzacorner@gmail.com','https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=600',4.3,'30-40 min',500,'Open'),
(5, 'Chennai Dosa House','Crispy dosas, idli and filter coffee','Mylapore, Chennai','Chennai','600004','9876543220','dosahouse@gmail.com','https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=600',4.6,'15-25 min',250,'Open'),
(5, 'Dragon Wok','Hakka noodles, fried rice and momos','Nungambakkam, Chennai','Chennai','600034','9876543221','dragonwok@gmail.com','https://images.unsplash.com/photo-1585032226651-759b368d7246?w=600',4.1,'25-35 min',350,'Open'),
(3, 'Sweet Tooth','Brownies, cakes and ice cream','Besant Nagar, Chennai','Chennai','600090','9876543224','sweettooth@gmail.com','https://images.unsplash.com/photo-1551024601-bec78aea704b?w=600',4.7,'20-30 min',300,'Open'),
(4, 'Green Bowl','Salads, smoothie bowls and grilled plates','Guindy, Chennai','Chennai','600032','9876543225','greenbowl@gmail.com','https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600',4.4,'20-30 min',350,'Open');

-- Menu items (restaurant_id, category_id follow inserts above)
INSERT INTO menu_items (restaurant_id, category_id, food_name, description, price, image_url, is_veg, availability, rating) VALUES
(1, 1, 'Margherita Pizza','Classic cheese pizza with basil and tomato',299.00,'https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400',true,true,4.5),
(1, 1, 'Pepperoni Pizza','Pepperoni with mozzarella and oregano',399.00,'https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400',false,true,4.6),
(1, 2, 'Chicken Burger','Spicy grilled chicken burger with fries',180.00,'https://images.unsplash.com/photo-1568909344668-6f14a07b56a0?w=400',false,true,4.3),
(1, 3, 'Chicken Biryani','Hyderabadi dum biryani with raita',250.00,'https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400',false,true,4.7),
(1, 6, 'Chocolate Brownie','Warm brownie with vanilla ice cream',120.00,'https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400',true,true,4.4),
(2, 3, 'Mutton Biryani','Aromatic mutton biryani with mirchi ka salan',320.00,'https://images.unsplash.com/photo-1631515243349-e0cb75fb8d3a?w=400',false,true,4.8),
(2, 3, 'Egg Biryani','Budget egg biryani with onion raita',180.00,'https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400',true,true,4.4),
(2, 4, 'Masala Dosa','Crispy dosa with chutney and sambar',90.00,'https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400',true,true,4.5),
(2, 4, 'Idli Sambar (4 pc)','Soft idlis with hot sambar',60.00,'https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400',true,true,4.3),
(3, 2, 'Veg Burger','Crispy veg patty burger with cheese',120.00,'https://images.unsplash.com/photo-1550547660-d9450f859349?w=400',true,true,4.2),
(3, 2, 'Double Cheese Burger','Double patty with extra cheese',199.00,'https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=400',true,true,4.4),
(3, 7, 'Cold Coffee','Creamy cold coffee with ice cream',80.00,'https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400',true,true,4.3),
(3, 7, 'Oreo Shake','Thick Oreo milkshake',140.00,'https://images.unsplash.com/photo-1577805947697-89e18249d767?w=400',true,true,4.5),
(4, 1, 'Farmhouse Pizza','Loaded veg pizza with capsicum and corn',349.00,'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400',true,true,4.6),
(4, 1, 'BBQ Chicken Pizza','Smoky BBQ chicken with onions',429.00,'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400',false,true,4.7),
(4, 7, 'Garlic Breadsticks','Buttery garlic bread with cheese dip',149.00,'https://images.unsplash.com/photo-1573140247632-f8fd74997d5c?w=400',true,true,4.2),
(5, 4, 'Ghee Roast Dosa','Golden ghee roast with filter coffee combo',130.00,'https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400',true,true,4.7),
(5, 4, 'Rava Dosa','Crispy rava dosa with onion and green chilli',110.00,'https://images.unsplash.com/photo-1630383249896-424e482df921?w=400',true,true,4.5),
(5, 7, 'Filter Coffee','Traditional South Indian filter coffee',40.00,'https://images.unsplash.com/photo-1617692855027-33b14f061079?w=400',true,true,4.8),
(6, 5, 'Veg Hakka Noodles','Street-style hakka noodles',140.00,'https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400',true,true,4.2),
(6, 5, 'Chicken Fried Rice','Smoky chicken fried rice with spring onion',180.00,'https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400',false,true,4.4),
(6, 5, 'Veg Momos (8 pc)','Steamed momos with spicy chutney',120.00,'https://images.unsplash.com/photo-1534422298391-e4f8c172dddb?w=400',true,true,4.3),
(7, 6, 'Chocolate Truffle Cake','Rich chocolate truffle pastry',90.00,'https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400',true,true,4.6),
(7, 6, 'Vanilla Ice Cream Scoop','Classic vanilla scoop',70.00,'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400',true,true,4.5),
(8, 8, 'Paneer Buddha Bowl','Grilled paneer with quinoa and veggies',220.00,'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400',true,true,4.5),
(8, 8, 'Chicken Salad','Grilled chicken with greens and dressing',240.00,'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400',false,true,4.4),
(8, 7, 'Mango Smoothie','Fresh mango smoothie, no added sugar',130.00,'https://images.unsplash.com/photo-1623065422902-30a2d299bbe4?w=400',true,true,4.6),
(1, 3, 'Veg Biryani','Fragrant veg dum biryani',190.00,'https://images.unsplash.com/photo-1596797038530-2c107229654b?w=400',true,true,4.4);

-- Addresses for customer ajay (user id 2)
INSERT INTO addresses (user_id, label, full_address, city, pincode, latitude, longitude, is_default) VALUES
(2, 'Home','No.10 Main Road, Anna Nagar','Chennai','600040',13.0843,80.2101,true),
(2, 'Work','No.5 Tech Park, Guindy','Chennai','600032',12.9916,80.2108,false);

-- Delivery partners
INSERT INTO delivery_partners (user_id, full_name, phone, email, vehicle_number, vehicle_type, availability, rating, current_latitude, current_longitude) VALUES
(6, 'Ramesh','9876543222','ramesh@gmail.com','TN01AB1234','Bike',true,4.6,13.0827,80.2707),
(7, 'Suresh','9876543223','suresh@delivery.com','TN02CD5678','Bike',true,4.5,13.0677,80.2377);

-- Favorites (user 2)
INSERT INTO favorites (user_id, restaurant_id) VALUES (2,1),(2,2),(2,5);
INSERT INTO favorites (user_id, menu_item_id) VALUES (2,1),(2,4),(2,8);

-- Reviews
INSERT INTO reviews (customer_id, restaurant_id, rating, comments) VALUES
(2,1,5,'Excellent food and quick delivery!'),
(2,2,4,'Great biryani, will order again'),
(2,5,5,'Best dosa in town, crispy and fresh'),
(2,7,5,'Brownie was warm and gooey, loved it'),
(2,8,4,'Fresh and healthy, perfect lunch');
