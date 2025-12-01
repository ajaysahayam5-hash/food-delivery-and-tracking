import { createContext, useContext, useState, useEffect } from 'react'
import api from '../services/api'

const AuthContext = createContext(null)
export const useAuth = () => useContext(AuthContext)

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem('user')
    return saved ? JSON.parse(saved) : null
  })
  const [token, setToken] = useState(() => localStorage.getItem('token'))

  const login = (data) => {
    localStorage.setItem('token', data.token)
    localStorage.setItem('user', JSON.stringify(data))
    setToken(data.token); setUser(data)
  }
  const logout = () => {
    localStorage.removeItem('token'); localStorage.removeItem('user')
    setToken(null); setUser(null)
  }
  return <AuthContext.Provider value={{ user, token, login, logout, isAuthenticated: !!token }}>{children}</AuthContext.Provider>
}
