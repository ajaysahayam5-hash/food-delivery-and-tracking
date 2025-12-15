import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getRestaurants, getCategories } from '../services/api'
import { demoRestaurants, demoCategories } from '../data/demoData'
import RestaurantCard from '../components/RestaurantCard'

export default function Home(){
  const [restaurants,setRestaurants]=useState([])
  const [categories,setCategories]=useState([])
  const [demoMode,setDemoMode]=useState(false)
  useEffect(()=>{
    getRestaurants().then(r=>{
      const list = r.data || []
      if(list.length===0){ setRestaurants(demoRestaurants.slice(0,6)); setDemoMode(true) }
      else setRestaurants(list.slice(0,6))
    }).catch(()=>{ setRestaurants(demoRestaurants.slice(0,6)); setDemoMode(true) })
    getCategories().then(r=>{
      const list = r.data || []
      setCategories(list.length===0 ? demoCategories : list)
    }).catch(()=>setCategories(demoCategories))
  },[])
  return (
    <>
      <section className="hero">
        <h1>Craving something delicious?</h1>
        <p>Order from your favourite restaurants and track delivery live</p>
        <div className="search-bar">
          <input placeholder="Search for restaurants, food..." onKeyDown={e=>{ if(e.key==='Enter') window.location='/search?q='+e.target.value }}/>
          <Link to="/restaurants" className="btn-primary" style={{padding:'14px 24px'}}>Explore</Link>
        </div>
        <div style={{marginTop:24, display:'flex', gap:12, justifyContent:'center', flexWrap:'wrap'}}>
          {categories.map(c=> <span key={c.id} className="badge" style={{padding:'8px 16px', fontSize:14}}>{c.name}</span>)}
        </div>
      </section>
      <div className="page">
        <h2 style={{marginBottom:16}}>Popular Restaurants</h2>
        {demoMode && <p style={{color:'#b7791f', background:'#fef3c7', padding:'8px 12px', borderRadius:8, marginBottom:12}}>Showing demo data — start the backend to see live data.</p>}
        <div className="grid grid-3">
          {restaurants.map(r=> <RestaurantCard key={r.id} r={r}/>)}
        </div>
        {restaurants.length===0 && <p style={{color:'#888', marginTop:20}}>No restaurants yet. Start the backend and seed the DB.</p>}
        <div style={{textAlign:'center', marginTop:24}}><Link to="/restaurants" className="btn-outline">View all restaurants</Link></div>
      </div>
    </>
  )
}
