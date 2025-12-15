import { useState } from 'react'
import { useSearchParams } from 'react-router-dom'
import { verifyOtp, resendOtp } from '../services/api'

export default function OtpVerification(){
  const [params]=useSearchParams()
  const email=params.get('email')||''
  const [form,setForm]=useState({email, otp:''})
  const [msg,setMsg]=useState('')
  const submit=async e=>{ e.preventDefault(); try{ const {data}=await verifyOtp(form); setMsg(data.message) } catch(ex){ setMsg(ex.response?.data?.message||'Failed') } }
  const resend=async()=>{ try{ const {data}=await resendOtp(form.email); setMsg(data.message+" OTP: "+(data.otp_dev||'see console')) } catch(ex){ setMsg('Failed') } }
  return (
    <div className="form">
      <h2>OTP Verification</h2>
      {msg && <p style={{marginBottom:12, color: msg.includes('success')?'green':'red'}}>{msg}</p>}
      <p style={{fontSize:13, color:'#666', marginBottom:12}}>For development, OTP is logged in backend console.</p>
      <form onSubmit={submit}>
        <input placeholder="Email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})} required/>
        <input placeholder="6-digit OTP" value={form.otp} onChange={e=>setForm({...form,otp:e.target.value})} required/>
        <button className="btn-primary" style={{width:'100%'}}>Verify</button>
      </form>
      <button className="btn-outline" style={{width:'100%', marginTop:10}} onClick={resend}>Resend OTP</button>
    </div>
  )
}
