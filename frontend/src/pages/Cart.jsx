import { useEffect, useState } from 'react'
import { getCart, updateCartItem, removeCartItem } from '../services/api'
import { Link } from 'react-router-dom'

export default function Cart(){
  const [items,setItems]=useState([])
  const load=()=> getCart().then(r=>setItems(r.data)).catch(()=>setItems([]))
  useEffect(()=>{ load() },[])
  const update=async(ci, delta)=>{
    const newQty = ci.quantity + delta
    if(newQty<=0) await removeCartItem(ci.id); else await updateCartItem(ci.id, newQty)
    load()
  }
  const remove=async id=>{ await removeCartItem(id); load() }
  const total = items.reduce((sum, x)=> sum + Number(x.cartItem.price)*x.cartItem.quantity, 0)
  return (
    <div className="page">
      <h2>Cart</h2>
      {items.length===0? <p>Your cart is empty. <Link to="/restaurants" style={{color:'#fc8019'}}>Browse restaurants</Link></p> : (
        <>
          {items.map(({cartItem, menuItem})=>(
            <div key={cartItem.id} className="card" style={{padding:12, display:'flex', justifyContent:'space-between', alignItems:'center', marginBottom:12}}>
              <div style={{display:'flex', gap:12, alignItems:'center'}}>
                <img src={menuItem?.imageUrl} alt="" style={{width:60, height:60, borderRadius:8, objectFit:'cover'}}/>
                <div><b>{menuItem?.foodName}</b><br/><span>₹{cartItem.price} x {cartItem.quantity}</span></div>
              </div>
              <div style={{display:'flex', gap:8, alignItems:'center'}}>
                <button className="btn-outline" onClick={()=>update(cartItem,-1)}>-</button>
                <span>{cartItem.quantity}</span>
                <button className="btn-outline" onClick={()=>update(cartItem,1)}>+</button>
                <button className="btn-primary" style={{background:'#e53935'}} onClick={()=>remove(cartItem.id)}>Remove</button>
              </div>
            </div>
          ))}
          <div className="card" style={{padding:16, marginTop:16}}>
            <h3>Total: ₹{total} + ₹40 delivery = ₹{total+40}</h3>
            <Link to="/checkout" className="btn-primary" style={{display:'inline-block', marginTop:12}}>Proceed to Checkout</Link>
          </div>
        </>
      )}
    </div>
  )
}
