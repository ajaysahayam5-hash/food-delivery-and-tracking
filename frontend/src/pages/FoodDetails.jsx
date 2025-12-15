import { useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { getMenuItem, addToCart } from '../services/api'
import { demoMenuItems } from '../data/demoData'
export default function FoodDetails(){
  const { id } = useParams()
  const [item,setItem]=useState(null)
  const [msg,setMsg]=useState('')
  useEffect(()=>{ getMenuItem(id).then(r=>setItem(r.data)).catch(()=>setItem(demoMenuItems.find(x=>String(x.id)===String(id)) || null)) },[id])
  const add=async()=>{ try{ await addToCart(id,1); setMsg('Added to cart')} catch{ setMsg('Login required')} }
  if(!item) return <div className="page">Loading...</div>
  return (
    <div className="page">
      <div style={{display:'flex', gap:20}}>
        <img src={item.imageUrl} alt={item.foodName} style={{width:300, height:300, objectFit:'cover', borderRadius:12}}/>
        <div>
          <h2>{item.foodName}</h2>
          <p>₹{item.price} • {item.isVeg?'Veg':'Non-Veg'} • ★{item.rating}</p>
          <p style={{marginTop:12, color:'#666'}}>{item.description}</p>
          <button className="btn-primary" style={{marginTop:16}} onClick={add}>Add to Cart</button>
          {msg && <p style={{color:'green', marginTop:8}}>{msg}</p>}
        </div>
      </div>
    </div>
  )
}
