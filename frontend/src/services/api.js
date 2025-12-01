import axios from 'axios'

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

const api = axios.create({ baseURL: API_URL })

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

api.interceptors.response.use(res => {
  const payload = res.data
  if (payload && typeof payload === 'object' && 'success' in payload && 'data' in payload) {
    res.data = payload.data
    res.apiMessage = payload.message
    res.apiSuccess = payload.success
  }
  return res
}, err => {
  if (err.response?.status === 401) {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    window.location.href = '/login'
  }
  return Promise.reject(err)
})

// Auth
export const register = (data) => api.post('/auth/register', data)
export const login = (data) => api.post('/auth/login', data)
export const verifyOtp = (data) => api.post('/auth/verify-otp', data)
export const resendOtp = (email) => api.post('/auth/resend-otp', { email })

// Restaurants
export const getRestaurants = (search) => api.get('/restaurants', { params: { search } })
export const getRestaurant = (id) => api.get(`/restaurants/${id}`)
export const createRestaurant = (data) => api.post('/restaurants', data)
export const updateRestaurant = (id, data) => api.put(`/restaurants/${id}`, data)
export const deleteRestaurant = (id) => api.delete(`/restaurants/${id}`)

// Menu Categories
export const getCategories = () => api.get('/categories')
export const getCategory = (id) => api.get(`/categories/${id}`)
export const createCategory = (data) => api.post('/categories', data)
export const updateCategory = (id, data) => api.put(`/categories/${id}`, data)
export const deleteCategory = (id) => api.delete(`/categories/${id}`)

// Menu Items
export const getMenuItems = (params) => api.get('/menu-items', { params })
export const getMenuItem = (id) => api.get(`/menu-items/${id}`)
export const getRestaurantMenu = (id) => api.get(`/menu-items/restaurant/${id}`)
export const createMenuItem = (data) => api.post('/menu-items', data)
export const updateMenuItem = (id, data) => api.put(`/menu-items/${id}`, data)
export const deleteMenuItem = (id) => api.delete(`/menu-items/${id}`)

// Cart
export const getCart = () => api.get('/cart')
export const addToCart = (menuItemId, quantity = 1) => api.post('/cart/items', { menuItemId, quantity })
export const updateCartItem = (id, quantity) => api.put(`/cart/items/${id}`, { quantity })
export const removeCartItem = (id) => api.delete(`/cart/items/${id}`)
export const clearCart = () => api.delete('/cart')

// Orders
export const placeOrder = (data) => api.post('/orders', data)
export const getOrders = () => api.get('/orders')
export const getOrder = (id) => api.get(`/orders/${id}`)
export const updateOrderStatus = (id, status) => api.put(`/orders/${id}/status`, { status })
export const cancelOrder = (id) => api.post(`/orders/${id}/cancel`)
export const getAllOrders = () => api.get('/orders/all')
export const getRestaurantOrders = (rid) => api.get(`/orders/restaurant/${rid}`)

// Delivery
export const getDeliveryByOrder = (orderId) => api.get(`/deliveries/order/${orderId}`)
export const updateDeliveryStatus = (id, status) => api.put(`/deliveries/${id}/status`, { status })
export const postLocation = (id, data) => api.post(`/deliveries/${id}/location`, data)
export const getLocation = (id) => api.get(`/deliveries/${id}/location`)
export const getDeliveryHistory = (id) => api.get(`/deliveries/${id}/history`)
export const getAllDeliveries = () => api.get('/deliveries')
export const getDeliveryPartnerDeliveries = (partnerId) => api.get(`/deliveries/partner/${partnerId}`)
export const assignDelivery = (orderId, partnerId) => api.post(`/deliveries/${orderId}/assign/${partnerId}`)
export const getDelivery = (id) => api.get(`/deliveries/${id}`)

// Delivery Partners
export const getDeliveryPartnerByUser = () => api.get('/delivery-partners/user')
export const getDeliveryPartners = () => api.get('/delivery-partners')
export const getDeliveryPartner = (id) => api.get(`/delivery-partners/${id}`)
export const createDeliveryPartner = (data) => api.post('/delivery-partners', data)
export const updateDeliveryPartner = (id, data) => api.put(`/delivery-partners/${id}`, data)

// Addresses
export const getAddresses = () => api.get('/addresses')
export const createAddress = (data) => api.post('/addresses', data)
export const updateAddress = (id, data) => api.put(`/addresses/${id}`, data)
export const deleteAddress = (id) => api.delete(`/addresses/${id}`)

// Reviews
export const getReviews = (restaurantId) => api.get(`/reviews/restaurant/${restaurantId}`)
export const createReview = (data) => api.post('/reviews', data)

// Favorites
export const getFavorites = () => api.get('/favorites')
export const addFavoriteRestaurant = (restaurantId) => api.post('/favorites', { restaurantId })
export const addFavoriteMenuItem = (menuItemId) => api.post('/favorites', { menuItemId })
export const removeFavorite = (id) => api.delete(`/favorites/${id}`)

// Users
export const getUser = () => api.get('/users/me')
export const getUsers = () => api.get('/users')
export const updateUser = (id, data) => api.put(`/users/${id}`, data)

// Payments
export const getPayment = (orderId) => api.get(`/payments/order/${orderId}`)
export const createPayment = (data) => api.post('/payments', data)

// Admin
export const getAdminStats = () => api.get('/admin/stats')

export default api