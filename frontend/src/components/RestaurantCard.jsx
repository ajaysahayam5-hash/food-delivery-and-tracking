import { Link } from 'react-router-dom'
export default function RestaurantCard({ r }){
  return (
    <Link to={`/restaurants/${r.id}`} className="card">
      <img src={r.imageUrl || 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400'} alt={r.restaurantName} style={{width:'100%', height:160, objectFit:'cover'}}/>
      <div style={{padding:14}}>
        <h3 style={{fontSize:16}}>{r.restaurantName}</h3>
        <p style={{fontSize:13, color:'#666', height:36, overflow:'hidden'}}>{r.description || r.address}</p>
        <div style={{display:'flex', justifyContent:'space-between', alignItems:'center', marginTop:8}}>
          <span className="rating">★ {r.rating || 4.3}</span>
          <span style={{fontSize:12, color:'#666'}}>{r.deliveryTime} • ₹{r.priceForTwo} for two</span>
        </div>
        <div style={{marginTop:6}}><span className="badge">{r.status || 'Open'}</span> <span style={{fontSize:12, color:'#888'}}> {r.city}</span></div>
      </div>
    </Link>
  )
}
