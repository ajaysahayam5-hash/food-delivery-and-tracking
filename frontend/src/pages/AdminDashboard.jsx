import { useEffect, useState } from 'react'
import { getAdminStats } from '../services/api'
import api from '../services/api'

export default function AdminDashboard(){
  const [stats,setStats]=useState(null)
  const [users,setUsers]=useState([])
  const [restaurants,setRestaurants]=useState([])
  useEffect(()=>{
    getAdminStats().then(r=>setStats(r.data)).catch(()=>{})
    api.get('/users').then(r=>setUsers(r.data)).catch(()=>{})
    api.get('/restaurants').then(r=>setRestaurants(r.data)).catch(()=>{})
  },[])
  return (
    <div className="page">
      <h2>Admin Dashboard</h2>
      {stats && <div className="grid" style={{gridTemplateColumns:'repeat(4,1fr)', gap:12, margin:'16px 0'}}>
        <div className="card" style={{padding:16, textAlign:'center'}}><h3>{stats.customers}</h3><p>Customers</p></div>
        <div className="card" style={{padding:16, textAlign:'center'}}><h3>{stats.restaurants}</h3><p>Restaurants</p></div>
        <div className="card" style={{padding:16, textAlign:'center'}}><h3>{stats.orders}</h3><p>Orders</p></div>
        <div className="card" style={{padding:16, textAlign:'center'}}><h3>{stats.deliveryPartners}</h3><p>Delivery Partners</p></div>
      </div>}
      <h3>Users</h3>
      <div className="card" style={{padding:12}}>
        {users.map(u=> <div key={u.id} style={{padding:'6px 0', borderBottom:'1px solid #eee'}}>{u.fullName} - {u.email} - {u.role}</div>)}
      </div>
      <h3 style={{marginTop:16}}>Restaurants</h3>
      <div className="card" style={{padding:12}}>
        {restaurants.map(r=> <div key={r.id} style={{padding:'6px 0'}}>{r.restaurantName} - {r.city} - {r.status}</div>)}
      </div>
    </div>
  )
}
