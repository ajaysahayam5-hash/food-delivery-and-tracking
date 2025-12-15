import { useEffect, useState } from 'react'
import { getUser, getAddresses, createAddress } from '../services/api'

export default function Profile(){
  const [user,setUser]=useState(null)
  const [addresses,setAddresses]=useState([])
  const [form,setForm]=useState({fullAddress:'', city:'', pincode:''})
  useEffect(()=>{ getUser().then(r=>setUser(r.data)).catch(()=>{}); getAddresses().then(r=>setAddresses(r.data)).catch(()=>{}) },[])
  const add=async e=>{ e.preventDefault(); const {data}=await createAddress({label:'Home', ...form}); setAddresses([...addresses,data]) }
  return (
    <div className="page">
      <h2>Profile</h2>
      {user && <div className="card" style={{padding:16, marginBottom:16}}><p><b>{user.fullName}</b> ({user.role})</p><p>{user.email} • {user.phone}</p><p>Verified: {user.emailVerified?'Yes':'No'}</p></div>}
      <h3>Addresses</h3>
      {addresses.map(a=> <div key={a.id} className="card" style={{padding:12, marginBottom:8}}>{a.fullAddress}, {a.city} - {a.pincode}</div>)}
      <form onSubmit={add} className="card" style={{padding:16, marginTop:12}}>
        <h4>Add Address</h4>
        <input placeholder="Full address" value={form.fullAddress} onChange={e=>setForm({...form,fullAddress:e.target.value})} style={{width:'100%', padding:8, marginBottom:8}} required/>
        <input placeholder="City" value={form.city} onChange={e=>setForm({...form,city:e.target.value})} style={{width:'100%', padding:8, marginBottom:8}}/>
        <input placeholder="Pincode" value={form.pincode} onChange={e=>setForm({...form,pincode:e.target.value})} style={{width:'100%', padding:8, marginBottom:8}}/>
        <button className="btn-primary">Save</button>
      </form>
    </div>
  )
}
