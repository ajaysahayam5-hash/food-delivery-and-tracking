export default function FoodCard({ item, onAdd }){
  return (
    <div className="card" style={{padding:12, display:'flex', gap:12}}>
      <img src={item.imageUrl || 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=200'} alt={item.foodName} style={{width:90, height:90, borderRadius:8, objectFit:'cover'}}/>
      <div style={{flex:1}}>
        <div style={{display:'flex', gap:6, alignItems:'center'}}><span style={{fontSize:10, border:'1px solid', padding:'1px 4px', color: item.isVeg ? 'green' : 'red'}}>●</span><h4 style={{fontSize:15}}>{item.foodName}</h4></div>
        <p style={{fontSize:12, color:'#666'}}>₹{item.price}</p>
        <p style={{fontSize:11, color:'#888'}}>{item.description?.slice(0,60)}</p>
        <div style={{marginTop:6}}><span className="rating" style={{fontSize:12}}>★ {item.rating} </span>{!item.availability && <span style={{color:'red', fontSize:12}}>Out of stock</span>}</div>
      </div>
      <button className="btn-primary" onClick={()=>onAdd(item)} disabled={!item.availability} style={{alignSelf:'center', padding:'8px 14px'}}>{item.availability?'ADD':'Unavailable'}</button>
    </div>
  )
}
