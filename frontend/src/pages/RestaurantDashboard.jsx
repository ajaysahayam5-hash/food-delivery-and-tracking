import { useEffect, useState } from 'react'
import { getRestaurantOrders, updateOrderStatus, getRestaurantMenu, createMenuItem, deleteMenuItem } from '../services/api'
import { useAuth } from '../context/AuthContext'

export default function RestaurantDashboard(){
  const { user } = useAuth()
  const [orders,setOrders]=useState([])
  const [menu,setMenu]=useState([])
  const [form,setForm]=useState({foodName:'', price:'', description:'', categoryId:1, imageUrl:''})
  const restaurantId=1 // demo: owner owns 1; in real app fetch by ownerId
  const load=()=>{ getRestaurantOrders(restaurantId).then(r=>setOrders(r.data)).catch(()=>{}); getRestaurantMenu(restaurantId).then(r=>setMenu(r.data)).catch(()=>{}) }
  useEffect(()=>{ load() },[])
  const add=async e=>{
    e.preventDefault();
    await createMenuItem({...form, price: parseFloat(form.price), restaurantId, categoryId: Number(form.categoryId)})
    setForm({foodName:'', price:'', description:'', categoryId:1, imageUrl:''}); load()
  }
  const del=async id=>{ await deleteMenuItem(id); load() }
  return (
    <div className="page">
      <h2>Restaurant Dashboard</h2>
      <p style={{color:'#666'}}>Logged as {user?.email}</p>
      <h3 style={{marginTop:16}}>Incoming Orders</h3>
      {orders.map(o=>(
        <div key={o.id} className="card" style={{padding:12, marginBottom:8, display:'flex', justifyContent:'space-between'}}>
          <span>Order #{o.id} - ₹{o.totalAmount} - {o.orderStatus}</span>
          <span>
            <button className="btn-outline" onClick={()=>updateOrderStatus(o.id,'CONFIRMED').then(load)}>Accept</button>
            <button className="btn-outline" onClick={()=>updateOrderStatus(o.id,'PREPARING').then(load)} style={{marginLeft:6}}>Preparing</button>
            <button className="btn-primary" onClick={()=>updateOrderStatus(o.id,'READY_FOR_PICKUP').then(load)} style={{marginLeft:6}}>Ready</button>
          </span>
        </div>
      ))}
      {orders.length===0 && <p>No orders.</p>}

      <h3 style={{marginTop:24}}>Menu Management</h3>
      <form onSubmit={add} className="card" style={{padding:16, display:'grid', gap:8}}>
        <input placeholder="Food name" value={form.foodName} onChange={e=>setForm({...form,foodName:e.target.value})} required/>
        <input placeholder="Price" type="number" value={form.price} onChange={e=>setForm({...form,price:e.target.value})} required/>
        <input placeholder="Description" value={form.description} onChange={e=>setForm({...form,description:e.target.value})}/>
        <input placeholder="Image URL" value={form.imageUrl} onChange={e=>setForm({...form,imageUrl:e.target.value})}/>
        <button className="btn-primary">Add Food</button>
      </form>
      <div className="grid" style={{marginTop:12}}>
        {menu.map(m=>(
          <div key={m.id} className="card" style={{padding:12, display:'flex', justifyContent:'space-between'}}>
            <span>{m.foodName} - ₹{m.price}</span>
            <button className="btn-outline" onClick={()=>del(m.id)}>Delete</button>
          </div>
        ))}
      </div>
    </div>
  )
}
