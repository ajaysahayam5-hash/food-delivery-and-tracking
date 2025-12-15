import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getFavorites, removeFavorite, getRestaurants, getMenuItems } from '../services/api'

export default function Favorites(){
  const [favs, setFavs] = useState([])
  const [names, setNames] = useState({ restaurants: {}, items: {} })
  const [err, setErr] = useState('')

  const load = () => {
    getFavorites().then(async r => {
      const list = r.data || []
      setFavs(list)
      try {
        const [rr, mm] = await Promise.all([getRestaurants().catch(()=>({data:[]})), getMenuItems().catch(()=>({data:[]}))])
        const rmap = {}; (rr.data||[]).forEach(x=>{ rmap[x.id]=x.restaurantName || x.name });
        const mmap = {}; (mm.data||[]).forEach(x=>{ mmap[x.id]=x.name });
        setNames({ restaurants: rmap, items: mmap })
      } catch {}
    }).catch(()=> setErr('Login required to view favorites'))
  }
  useEffect(()=>{ load() },[])

  const remove = async (id) => { await removeFavorite(id); load() }

  return (
    <div className="page">
      <h2>Favorites</h2>
      {err && <p className="error">{err}</p>}
      {favs.length===0 && !err && <p>No favorites yet. Browse <Link to="/restaurants">restaurants</Link> or <Link to="/search">food</Link> and tap favorite.</p>}
      <div className="grid">
        {favs.map(f=>(
          <div key={f.id} className="card">
            <h4>{f.restaurantId ? (names.restaurants[f.restaurantId] || `Restaurant #${f.restaurantId}`) : (names.items[f.menuItemId] || `Dish #${f.menuItemId}`)}</h4>
            <p className="muted">{f.restaurantId ? <Link to={`/restaurants/${f.restaurantId}`}>View restaurant</Link> : <Link to={`/food/${f.menuItemId}`}>View dish</Link>}</p>
            <button onClick={()=>remove(f.id)}>Remove</button>
          </div>
        ))}
      </div>
    </div>
  )
}
