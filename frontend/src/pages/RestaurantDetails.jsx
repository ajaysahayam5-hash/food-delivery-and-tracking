import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { getRestaurant, getRestaurantMenu, addToCart } from '../services/api'
import { demoRestaurants, demoMenuItems } from '../data/demoData'
import FoodCard from '../components/FoodCard'

export default function RestaurantDetails(){
  const { id } = useParams()
  const [r,setR]=useState(null)
  const [menu,setMenu]=useState([])
  const [msg,setMsg]=useState('')
  const [demoMode,setDemoMode]=useState(false)
  useEffect(()=>{
    getRestaurant(id).then(res=>setR(res.data)).catch(()=>{
      setR(demoRestaurants.find(x=>String(x.id)===String(id)) || null); setDemoMode(true)
    })
    getRestaurantMenu(id).then(res=>{
      const data = res.data || []
      if(data.length===0){ setMenu(demoMenuItems.filter(m=>String(m.restaurantId)===String(id))); setDemoMode(true) }
      else setMenu(data)
    }).catch(()=>{ setMenu(demoMenuItems.filter(m=>String(m.restaurantId)===String(id))); setDemoMode(true) })
  },[id])
  const add=async(item)=>{
    try{ await addToCart(item.id,1); setMsg(item.foodName+' added to cart') ; setTimeout(()=>setMsg(''),2000) } catch(e){ setMsg('Please login to add to cart') }
  }
  if(!r) return <div className="page">Loading...</div>
  return (
    <div className="page">
      {demoMode && <p style={{color:'#b7791f', background:'#fef3c7', padding:'8px 12px', borderRadius:8, marginBottom:12}}>Showing demo data — start the backend to see live data.</p>}
      <div style={{display:'flex', gap:20, flexWrap:'wrap'}}>
        <img src={r.imageUrl} alt={r.restaurantName} style={{width:320, height:200, objectFit:'cover', borderRadius:12}}/>
        <div>
          <h2>{r.restaurantName}</h2>
          <p style={{color:'#666'}}>{r.description} • {r.city}</p>
          <p className="rating">★ {r.rating} • {r.deliveryTime} • ₹{r.priceForTwo} for two</p>
          <p style={{fontSize:13, color:'#888'}}>{r.address} • {r.phone}</p>
        </div>
      </div>
      {msg && <p style={{marginTop:12, color:'green'}}>{msg}</p>}
      <h3 style={{marginTop:24}}>Menu</h3>
      <div className="grid" style={{marginTop:12}}>
        {menu.map(m=> <FoodCard key={m.id} item={m} onAdd={add}/>)}
        {menu.length===0 && <p style={{color:'#888'}}>No menu items yet.</p>}
      </div>
    </div>
  )
}
