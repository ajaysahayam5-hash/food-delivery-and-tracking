import { useEffect, useState } from 'react'
import { getRestaurants } from '../services/api'
import { demoRestaurants } from '../data/demoData'
import RestaurantCard from '../components/RestaurantCard'

export default function Restaurants(){
  const [list,setList]=useState([])
  const [q,setQ]=useState('')
  const [demoMode,setDemoMode]=useState(false)
  const load=()=>{
    const query = q||undefined
    getRestaurants(query).then(r=>{
      const data = r.data || []
      if(data.length===0){
        const filtered = query
          ? demoRestaurants.filter(x=>x.restaurantName.toLowerCase().includes(query.toLowerCase()))
          : demoRestaurants
        setList(filtered); setDemoMode(true)
      } else { setList(data); setDemoMode(false) }
    }).catch(()=>{
      const filtered = query
        ? demoRestaurants.filter(x=>x.restaurantName.toLowerCase().includes(query.toLowerCase()))
        : demoRestaurants
      setList(filtered); setDemoMode(true)
    })
  }
  useEffect(()=>{ load() },[])
  return (
    <div className="page">
      <h2>Restaurants</h2>
      <div style={{display:'flex', gap:8, margin:'16px 0'}}>
        <input placeholder="Search restaurants..." value={q} onChange={e=>setQ(e.target.value)} style={{flex:1, padding:10, border:'1px solid #ddd', borderRadius:8}}/>
        <button className="btn-primary" onClick={load}>Search</button>
      </div>
      {demoMode && <p style={{color:'#b7791f', background:'#fef3c7', padding:'8px 12px', borderRadius:8}}>Showing demo data — start the backend to see live data.</p>}
      <div className="grid grid-3">
        {list.map(r=> <RestaurantCard key={r.id} r={r}/>)}
      </div>
      {list.length===0 && <p style={{color:'#888'}}>No restaurants found.</p>}
    </div>
  )
}
