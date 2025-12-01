import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider, useAuth } from './context/AuthContext'
import Navbar from './components/Navbar'
import Footer from './components/Footer'
import Home from './pages/Home'
import Login from './pages/Login'
import Register from './pages/Register'
import OtpVerification from './pages/OtpVerification'
import Restaurants from './pages/Restaurants'
import RestaurantDetails from './pages/RestaurantDetails'
import FoodSearch from './pages/FoodSearch'
import FoodDetails from './pages/FoodDetails'
import Cart from './pages/Cart'
import Checkout from './pages/Checkout'
import OrderConfirmation from './pages/OrderConfirmation'
import MyOrders from './pages/MyOrders'
import OrderDetails from './pages/OrderDetails'
import LiveOrderTracking from './pages/LiveOrderTracking'
import Profile from './pages/Profile'
import Favorites from './pages/Favorites'
import RestaurantDashboard from './pages/RestaurantDashboard'
import DeliveryDashboard from './pages/DeliveryDashboard'
import AdminDashboard from './pages/AdminDashboard'
import { Protected, CustomerOnly, RestaurantOnly, DeliveryOnly, AdminOnly } from './components/ProtectedRoute'

function PublicOnly({ children }) {
  const { isAuthenticated } = useAuth()
  if (isAuthenticated) return <Navigate to="/" replace />
  return children
}

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home/>}/>
      <Route path="/login" element={<PublicOnly><Login/></PublicOnly>}/>
      <Route path="/register" element={<PublicOnly><Register/></PublicOnly>}/>
      <Route path="/otp" element={<OtpVerification/>}/>
      <Route path="/restaurants" element={<Restaurants/>}/>
      <Route path="/restaurants/:id" element={<RestaurantDetails/>}/>
      <Route path="/search" element={<FoodSearch/>}/>
      <Route path="/food/:id" element={<FoodDetails/>}/>
      <Route path="/cart" element={<CustomerOnly><Cart/></CustomerOnly>}/>
      <Route path="/checkout" element={<CustomerOnly><Checkout/></CustomerOnly>}/>
      <Route path="/order-confirmation/:id" element={<OrderConfirmation/>}/>
      <Route path="/orders" element={<CustomerOnly><MyOrders/></CustomerOnly>}/>
      <Route path="/orders/:id" element={<CustomerOnly><OrderDetails/></CustomerOnly>}/>
      <Route path="/tracking/:id" element={<CustomerOnly><LiveOrderTracking/></CustomerOnly>}/>
      <Route path="/profile" element={<CustomerOnly><Profile/></CustomerOnly>}/>
      <Route path="/favorites" element={<CustomerOnly><Favorites/></CustomerOnly>}/>
      <Route path="/restaurant/dashboard" element={<RestaurantOnly><RestaurantDashboard/></RestaurantOnly>}/>
      <Route path="/delivery/dashboard" element={<DeliveryOnly><DeliveryDashboard/></DeliveryOnly>}/>
      <Route path="/admin" element={<AdminOnly><AdminDashboard/></AdminOnly>}/>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}

export default function App(){
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar/>
        <AppRoutes/>
        <Footer/>
      </BrowserRouter>
    </AuthProvider>
  )
}