import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { login } from '../services/api'
import { useAuth } from '../context/AuthContext'

export default function Login(){
  const [form,setForm]=useState({email:'', password:''})
  const [err,setErr]=useState('')
  const nav=useNavigate()
  const { login: doLogin } = useAuth()
  const submit=async e=>{
    e.preventDefault(); setErr('')
    try{ const {data}=await login(form); doLogin(data); if(data.role==='ADMIN') nav('/admin'); else if(data.role==='RESTAURANT') nav('/restaurant/dashboard'); else if(data.role==='DELIVERY_PARTNER') nav('/delivery/dashboard'); else nav('/'); } catch(ex){ setErr(ex.response?.data?.message || 'Login failed') }
  }
  return (
    <div className="form">
      <h2>Login</h2>
      {err && <p style={{color:'red', marginBottom:12}}>{err}</p>}
      <form onSubmit={submit}>
        <input placeholder="Email" type="email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})} required/>
        <input placeholder="Password" type="password" value={form.password} onChange={e=>setForm({...form,password:e.target.value})} required/>
        <button className="btn-primary" style={{width:'100%'}}>Login</button>
      </form>
      <p style={{marginTop:12, textAlign:'center'}}>No account? <Link to="/register" style={{color:'#fc8019'}}>Register</Link></p>
      <div style={{marginTop:16, fontSize:12, background:'#f5f5f5', padding:12, borderRadius:8}}>
        <b>Demo accounts (password: password123 if seeded via backend registration, or 12345 for sql sample if not hashed)</b><br/>
        admin@fooddelivery.com (ADMIN) • ajay@gmail.com (CUSTOMER)<br/> owner@foodparadise.com (RESTAURANT) • ramesh@gmail.com (DELIVERY)
      </div>
    </div>
  )
}
