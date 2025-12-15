// Static demo data — used as fallback when backend is empty/unreachable.
// Shapes match backend entities: RestaurantCard uses restaurantName/description/rating/deliveryTime/priceForTwo/status/city/imageUrl,
// FoodCard uses foodName/price/description/rating/isVeg/availability/imageUrl.

export const demoCategories = [
  { id: 1, name: 'Pizza', description: 'Italian pizzas', imageUrl: 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400' },
  { id: 2, name: 'Burger', description: 'Juicy burgers', imageUrl: 'https://images.unsplash.com/photo-1568909344668-6f14a07b56a0?w=400' },
  { id: 3, name: 'Biryani', description: 'Hyderabadi dum biryani', imageUrl: 'https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400' },
  { id: 4, name: 'South Indian', description: 'Dosa, idli and filter coffee', imageUrl: 'https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400' },
  { id: 5, name: 'Chinese', description: 'Noodles, fried rice, momos', imageUrl: 'https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400' },
  { id: 6, name: 'Dessert', description: 'Brownies and ice cream', imageUrl: 'https://images.unsplash.com/photo-1551024601-bec78aea704b?w=400' },
  { id: 7, name: 'Beverages', description: 'Shakes and coffee', imageUrl: 'https://images.unsplash.com/photo-1544145945-f90425340c7e?w=400' },
  { id: 8, name: 'Healthy', description: 'Salads and bowls', imageUrl: 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400' },
]

export const demoRestaurants = [
  { id: 1, restaurantName: 'Food Paradise', description: 'Authentic North and South Indian delights', address: 'Anna Nagar, Chennai', city: 'Chennai', phone: '9876543210', imageUrl: 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=600', rating: 4.4, deliveryTime: '30-40 min', priceForTwo: 400, status: 'Open' },
  { id: 2, restaurantName: 'Spice Villa', description: 'Hyderabadi biryani and curries', address: 'T Nagar, Chennai', city: 'Chennai', phone: '9876543216', imageUrl: 'https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=600', rating: 4.5, deliveryTime: '25-35 min', priceForTwo: 350, status: 'Open' },
  { id: 3, restaurantName: 'Burger Hub', description: 'Crispy burgers, fries and shakes', address: 'Velachery, Chennai', city: 'Chennai', phone: '9876543217', imageUrl: 'https://images.unsplash.com/photo-1550547660-d9450f859349?w=600', rating: 4.2, deliveryTime: '20-30 min', priceForTwo: 300, status: 'Open' },
  { id: 4, restaurantName: 'Pizza Corner', description: 'Wood-fired pizzas and garlic bread', address: 'Adyar, Chennai', city: 'Chennai', phone: '9876543218', imageUrl: 'https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=600', rating: 4.3, deliveryTime: '30-40 min', priceForTwo: 500, status: 'Open' },
  { id: 5, restaurantName: 'Chennai Dosa House', description: 'Crispy dosas, idli and filter coffee', address: 'Mylapore, Chennai', city: 'Chennai', phone: '9876543220', imageUrl: 'https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=600', rating: 4.6, deliveryTime: '15-25 min', priceForTwo: 250, status: 'Open' },
  { id: 6, restaurantName: 'Dragon Wok', description: 'Hakka noodles, fried rice and momos', address: 'Nungambakkam, Chennai', city: 'Chennai', phone: '9876543221', imageUrl: 'https://images.unsplash.com/photo-1585032226651-759b368d7246?w=600', rating: 4.1, deliveryTime: '25-35 min', priceForTwo: 350, status: 'Open' },
  { id: 7, restaurantName: 'Sweet Tooth', description: 'Brownies, cakes and ice cream', address: 'Besant Nagar, Chennai', city: 'Chennai', phone: '9876543224', imageUrl: 'https://images.unsplash.com/photo-1551024601-bec78aea704b?w=600', rating: 4.7, deliveryTime: '20-30 min', priceForTwo: 300, status: 'Open' },
  { id: 8, restaurantName: 'Green Bowl', description: 'Salads, smoothie bowls and grilled plates', address: 'Guindy, Chennai', city: 'Chennai', phone: '9876543225', imageUrl: 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600', rating: 4.4, deliveryTime: '20-30 min', priceForTwo: 350, status: 'Open' },
]

export const demoMenuItems = [
  { id: 1, restaurantId: 1, categoryId: 1, foodName: 'Margherita Pizza', description: 'Classic cheese pizza with basil and tomato', price: 299, imageUrl: 'https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400', isVeg: true, availability: true, rating: 4.5 },
  { id: 2, restaurantId: 1, categoryId: 1, foodName: 'Pepperoni Pizza', description: 'Pepperoni with mozzarella and oregano', price: 399, imageUrl: 'https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400', isVeg: false, availability: true, rating: 4.6 },
  { id: 3, restaurantId: 1, categoryId: 2, foodName: 'Chicken Burger', description: 'Spicy grilled chicken burger with fries', price: 180, imageUrl: 'https://images.unsplash.com/photo-1568909344668-6f14a07b56a0?w=400', isVeg: false, availability: true, rating: 4.3 },
  { id: 4, restaurantId: 1, categoryId: 3, foodName: 'Chicken Biryani', description: 'Hyderabadi dum biryani with raita', price: 250, imageUrl: 'https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400', isVeg: false, availability: true, rating: 4.7 },
  { id: 6, restaurantId: 2, categoryId: 3, foodName: 'Mutton Biryani', description: 'Aromatic mutton biryani with mirchi ka salan', price: 320, imageUrl: 'https://images.unsplash.com/photo-1631515243349-e0cb75fb8d3a?w=400', isVeg: false, availability: true, rating: 4.8 },
  { id: 8, restaurantId: 2, categoryId: 4, foodName: 'Masala Dosa', description: 'Crispy dosa with chutney and sambar', price: 90, imageUrl: 'https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400', isVeg: true, availability: true, rating: 4.5 },
  { id: 10, restaurantId: 3, categoryId: 2, foodName: 'Veg Burger', description: 'Crispy veg patty burger with cheese', price: 120, imageUrl: 'https://images.unsplash.com/photo-1550547660-d9450f859349?w=400', isVeg: true, availability: true, rating: 4.2 },
  { id: 12, restaurantId: 3, categoryId: 7, foodName: 'Cold Coffee', description: 'Creamy cold coffee with ice cream', price: 80, imageUrl: 'https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400', isVeg: true, availability: true, rating: 4.3 },
  { id: 14, restaurantId: 4, categoryId: 1, foodName: 'Farmhouse Pizza', description: 'Loaded veg pizza with capsicum and corn', price: 349, imageUrl: 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400', isVeg: true, availability: true, rating: 4.6 },
  { id: 17, restaurantId: 5, categoryId: 4, foodName: 'Ghee Roast Dosa', description: 'Golden ghee roast with filter coffee combo', price: 130, imageUrl: 'https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400', isVeg: true, availability: true, rating: 4.7 },
  { id: 20, restaurantId: 6, categoryId: 5, foodName: 'Veg Hakka Noodles', description: 'Street-style hakka noodles', price: 140, imageUrl: 'https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400', isVeg: true, availability: true, rating: 4.2 },
  { id: 23, restaurantId: 7, categoryId: 6, foodName: 'Chocolate Truffle Cake', description: 'Rich chocolate truffle pastry', price: 90, imageUrl: 'https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400', isVeg: true, availability: true, rating: 4.6 },
  { id: 25, restaurantId: 8, categoryId: 8, foodName: 'Paneer Buddha Bowl', description: 'Grilled paneer with quinoa and veggies', price: 220, imageUrl: 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400', isVeg: true, availability: true, rating: 4.5 },
  { id: 27, restaurantId: 8, categoryId: 7, foodName: 'Mango Smoothie', description: 'Fresh mango smoothie, no added sugar', price: 130, imageUrl: 'https://images.unsplash.com/photo-1623065422902-30a2d299bbe4?w=400', isVeg: true, availability: true, rating: 4.6 },
]
