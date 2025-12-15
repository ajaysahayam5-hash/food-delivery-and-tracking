import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getOrders } from '../services/api'

export default function MyOrders(){
  const [orders,setOrders]=useState([])
  useEffect(()=>{ getOrders().then(r=>setOrders(r.data)).catch(()=>{}) },[])
  return (
    <div className="page">
      <h2>My Orders</h2>
      {orders.map(o=>(
        <Link key={o.id} to={`/orders/${o.id}`} className="card" style={{display:'block', padding:16, marginBottom:12}}>
          <div style={{display:'flex', justifyContent:'space-between'}}>
            <b>Order #{o.id}</b> <span className="badge">{o.orderStatus}</span>
          </div>
          <p style={{fontSize:13, color:'#666'}}>₹{o.totalAmount} • {new Date(o.createdAt).toLocaleString()}</p>
          <p style={{fontSize:12}}>{o.deliveryAddress}</p>
        </Link>
      ))}
      {orders.length===0 && <p>No orders yet.</p>}
    </div>
  )
}
