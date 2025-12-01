import { Navigate, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export function Protected({ children, allowedRoles }) {
  const { user, isAuthenticated } = useAuth()
  const location = useLocation()

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />
  }

  if (allowedRoles && allowedRoles.length > 0 && !allowedRoles.includes(user?.role)) {
    return <Navigate to="/" replace />
  }

  return children
}

export function CustomerOnly({ children }) {
  return <Protected allowedRoles={['CUSTOMER', 'ADMIN']}>{children}</Protected>
}

export function RestaurantOnly({ children }) {
  return <Protected allowedRoles={['RESTAURANT', 'ADMIN']}>{children}</Protected>
}

export function DeliveryOnly({ children }) {
  return <Protected allowedRoles={['DELIVERY_PARTNER', 'ADMIN']}>{children}</Protected>
}

export function AdminOnly({ children }) {
  return <Protected allowedRoles={['ADMIN']}>{children}</Protected>
}