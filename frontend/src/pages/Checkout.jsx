import { useEffect, useState } from 'react'
import { getAddresses, createAddress, placeOrder, getCart } from '../services/api'
import { useNavigate } from 'react-router-dom'

export default function Checkout(){
  const [addresses,setAddresses]=useState([])
  const [selected,setSelected]=useState(null)
  const [payment,setPayment]=useState('CASH')
  const [newAddr,setNewAddr]=useState({fullAddress:'', city:'', pincode:''})
  const [cart,setCart]=useState([])
  const nav=useNavigate()
  const load=()=>{ getAddresses().then(r=>setAddresses(r.data)).catch(()=>{}); getCart().then(r=>setCart(r.data)).catch(()=>{}) }
  useEffect(()=>{ load() },[])
  const addAddr=async e=>{
    e.preventDefault();
    const {data}=await createAddress({label:'Home', ...newAddr, city:newAddr.city})
    setAddresses([...addresses, data]); setNewAddr({fullAddress:'', city:'', pincode:''})
  }
  const order=async()=>{
    if(!selected) return alert('Select address')
    const restaurantId = cart[0]?.menuItem?.restaurantId || null
    const deliveryAddress = addresses.find(a=>a.id===selected)?.fullAddress || 'Default'
    const {data}=await placeOrder({restaurantId, deliveryAddress, deliveryAddressId: selected, paymentMethod: payment})
    nav('/order-confirmation/'+data.id)
  }
  return (
    <div className="page">
      <h2>Checkout</h2>
      <div className="grid" style={{gridTemplateColumns:'1fr 360px', gap:20}}>
        <div>
          <h3>Select Address</h3>
          {addresses.map(a=>(
            <div key={a.id} onClick={()=>setSelected(a.id)} className="card" style={{padding:12, marginBottom:8, border: selected===a.id?'2px solid #fc8019':'1px solid #eee', cursor:'pointer'}}>
              <b>{a.label}</b> - {a.fullAddress}, {a.city} - {a.pincode}
            </div>
          ))}
          <form onSubmit={addAddr} style={{marginTop:16}} className="card" >
            <div style={{padding:16}}>
              <h4>Add new address</h4>
              <input placeholder="Full address" value={newAddr.fullAddress} onChange={e=>setNewAddr({...newAddr,fullAddress:e.target.value})} style={{width:'100%', padding:8, marginBottom:8}} required/>
              <input placeholder="City" value={newAddr.city} onChange={e=>setNewAddr({...newAddr,city:e.target.value})} style={{width:'100%', padding:8, marginBottom:8}}/>
              <input placeholder="Pincode" value={newAddr.pincode} onChange={e=>setNewAddr({...newAddr,pincode:e.target.value})} style={{width:'100%', padding:8, marginBottom:8}}/>
              <button className="btn-outline" style={{width:'100%'}}>Add Address</button>
            </div>
          </form>
          <h3 style={{marginTop:20}}>Payment Method</h3>
          <select value={payment} onChange={e=>setPayment(e.target.value)} style={{padding:10, width:'100%', marginTop:8}}>
            <option value="CASH">Cash on Delivery</option>
            <option value="UPI">UPI</option>
            <option value="Card">Card</option>
          </select>
        </div>
        <div className="card" style={{padding:16, height:'fit-content'}}>
          <h3>Order Summary</h3>
          <p>{cart.length} items</p>
          <button className="btn-primary" style={{width:'100%', marginTop:16}} onClick={order}>Place Order</button>
        </div>
      </div>
    </div>
  )
}
