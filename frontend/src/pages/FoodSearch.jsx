import { useState } from 'react'
import { getMenuItems, addToCart } from '../services/api'
import { demoMenuItems } from '../data/demoData'
import FoodCard from '../components/FoodCard'

export default function FoodSearch(){
  const [q,setQ]=useState(''), [items,setItems]=useState([]), [msg,setMsg]=useState('')
  const search=async()=>{
    try{
      const {data}=await getMenuItems(q?{search:q}:undefined)
      if(data && data.length>0){ setItems(data); return }
    }catch{}
    const filtered = q
      ? demoMenuItems.filter(i=>i.foodName.toLowerCase().includes(q.toLowerCase()))
      : demoMenuItems
    setItems(filtered)
  }
  const add=async(item)=>{ try{ await addToCart(item.id,1); setMsg('Added '+item.foodName)} catch{ setMsg('Login required') } }
  return (
    <div className="page">
      <h2>Search Food</h2>
      <div style={{display:'flex', gap:8, margin:'16px 0'}}>
        <input placeholder="Search food..." value={q} onChange={e=>setQ(e.target.value)} style={{flex:1, padding:10, border:'1px solid #ddd', borderRadius:8}}/>
        <button className="btn-primary" onClick={search}>Search</button>
      </div>
      {msg && <p style={{color:'green'}}>{msg}</p>}
      <div className="grid">
        {items.map(i=> <FoodCard key={i.id} item={i} onAdd={add}/>)}
      </div>
    </div>
  )
}
