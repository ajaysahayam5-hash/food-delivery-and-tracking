import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { register } from '../services/api'
import { useAuth } from '../context/AuthContext'

export default function Register(){
  const [form,setForm]=useState({full_name:'', email:'', phone:'', password:'', role:'CUSTOMER'})
  const [err,setErr]=useState('')
  const nav=useNavigate()
  const { login } = useAuth()
  const submit=async e=>{
    e.preventDefault(); setErr('')
    const payload={full_name:form.full_name,email:form.email,phone:form.phone||undefined,password:form.password,role:form.role}
    try{ const {data}=await register(payload); login(data); nav('/otp?email='+encodeURIComponent(form.email)) } catch(ex){ setErr(ex.response?.data?.errors?.[0]?.msg || ex.response?.data?.error || ex.message) }
  }
  return (
    <div className="form">
      <h2>Register</h2>
      {err && <p style={{color:'red'}}>{err}</p>}
      <form onSubmit={submit}>
        <input placeholder="Full Name" value={form.full_name} onChange={e=>setForm({...form,full_name:e.target.value})} required/>
        <input placeholder="Email" type="email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})} required/>
        <input placeholder="Phone" value={form.phone} onChange={e=>setForm({...form,phone:e.target.value})} />
        <input placeholder="Password (min 6)" type="password" value={form.password} onChange={e=>setForm({...form,password:e.target.value})} required/>
        <select value={form.role} onChange={e=>setForm({...form,role:e.target.value})}>
          <option value="CUSTOMER">Customer</option>
          <option value="RESTAURANT">Restaurant</option>
          <option value="DELIVERY_PARTNER">Delivery Partner</option>
        </select>
        <button className="btn-primary" style={{width:'100%'}}>Register</button>
      </form>
      <p style={{marginTop:12, textAlign:'center'}}>Already have account? <Link to="/login" style={{color:'#fc8019'}}>Login</Link></p>
    </div>
  )
}
