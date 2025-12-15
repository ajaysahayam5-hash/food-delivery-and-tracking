import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { getOrder, cancelOrder, updateOrderStatus } from '../services/api'
import { useAuth } from '../context/AuthContext'

export default function OrderDetails(){
  const { id } = useParams()
  const [o,setO]=useState(null)
  const { user } = useAuth()
  const load=()=> getOrder(id).then(r=>setO(r.data)).catch(()=>{})
  useEffect(()=>{ load() },[id])
  const cancel=async()=>{ await cancelOrder(id); load() }
  const advance=async(status)=>{ await updateOrderStatus(id,status); load() }
  if(!o) return <div className="page">Loading...</div>
  const steps=['PLACED','CONFIRMED','PREPARING','READY_FOR_PICKUP','PICKED_UP','OUT_FOR_DELIVERY','DELIVERED']
  const idx=steps.indexOf(o.orderStatus)
  return (
    <div className="page">
      <h2>Order #{o.id}</h2>
      <p>Status: <b>{o.orderStatus}</b> • Payment: {o.paymentStatus} ({o.paymentMethod}) • ₹{o.totalAmount}</p>
      <div className="order-steps">
        {steps.map((s,i)=> <div key={s} className={`step ${i<idx?'done': i===idx?'active':''}`}>{s}</div>)}
        {o.orderStatus==='CANCELLED' && <div className="step" style={{background:'#e53935', color:'#fff'}}>CANCELLED</div>}
      </div>
      <p style={{marginTop:12}}>{o.deliveryAddress}</p>
      <div style={{marginTop:16, display:'flex', gap:8, flexWrap:'wrap'}}>
        <Link to={`/tracking/${o.id}`} className="btn-primary">Track Delivery</Link>
        {['PLACED','CONFIRMED'].includes(o.orderStatus) && <button className="btn-outline" onClick={cancel}>Cancel Order</button>}
        {user?.role==='RESTAURANT' && (
          <>
            <button className="btn-outline" onClick={()=>advance('CONFIRMED')}>Confirm</button>
            <button className="btn-outline" onClick={()=>advance('PREPARING')}>Preparing</button>
            <button className="btn-outline" onClick={()=>advance('READY_FOR_PICKUP')}>Ready</button>
          </>
        )}
        {user?.role==='DELIVERY_PARTNER' && (
          <>
            <button className="btn-outline" onClick={()=>advance('PICKED_UP')}>Picked Up</button>
            <button className="btn-outline" onClick={()=>advance('OUT_FOR_DELIVERY')}>Out for Delivery</button>
            <button className="btn-primary" onClick={()=>advance('DELIVERED')}>Delivered</button>
          </>
        )}
      </div>
    </div>
  )
}
