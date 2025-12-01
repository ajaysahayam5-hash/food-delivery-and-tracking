import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
export default function Navbar(){
  const { user, logout, isAuthenticated } = useAuth()
  const nav = useNavigate()
  const handleLogout = () => { logout(); nav('/login') }
  return (
    <nav className="navbar">
      <div className="navbar-inner">
        <Link to="/" className="logo">🍔 FoodExpress</Link>
        <div className="nav-links">
          <Link to="/">Home</Link>
          <Link to="/restaurants">Restaurants</Link>
          <Link to="/search">Search</Link>
          <Link to="/cart">Cart</Link>
          {isAuthenticated ? (
            <>
              <Link to="/orders">My Orders</Link>
              <Link to="/profile">Profile</Link>
              {user?.role==='RESTAURANT' && <Link to="/restaurant/dashboard">Dashboard</Link>}
              {user?.role==='DELIVERY_PARTNER' && <Link to="/delivery/dashboard">Delivery</Link>}
              {user?.role==='ADMIN' && <Link to="/admin">Admin</Link>}
              <span style={{fontSize:13, color:'#666'}}>{user?.fullName} ({user?.role})</span>
              <button className="btn-primary" onClick={handleLogout}>Logout</button>
            </>
          ) : (
            <>
              <Link to="/login">Login</Link>
              <Link to="/register" className="btn-primary">Sign Up</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  )
}
