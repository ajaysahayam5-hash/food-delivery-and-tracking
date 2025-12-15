import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { getDeliveryByOrder, getLocation, postLocation } from '../services/api'
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'

export default function LiveOrderTracking(){
  const { id } = useParams()
  const [delivery,setDelivery]=useState(null)
  const [loc,setLoc]=useState(null)
  const [poll, setPoll]=useState(true)

  const load=async()=>{
    const {data}=await getDeliveryByOrder(id)
    setDelivery(data)
    try{ const {data:ld}=await getLocation(data.id); setLoc(ld) } catch{}
  }
  useEffect(()=>{ load(); const iv=setInterval(()=> poll && load(), 5000); return()=>clearInterval(iv) },[poll])

  const simulate=async()=>{
    if(!delivery) return
    const lat= 13.0827 + Math.random()*0.02, lon=80.2707+Math.random()*0.02
    await postLocation(delivery.id, {latitude:lat, longitude:lon, currentLocation:`Moving - ${new Date().toLocaleTimeString()}`})
    load()
  }

  return (
    <div className="page">
      <h2>Live Tracking - Order #{id}</h2>
      <p>Status: <b>{delivery?.trackingStatus}</b> • Estimated: {delivery?.estimatedTime}</p>
      <div style={{display:'flex', gap:12, margin:'12px 0'}}>
        <button className="btn-primary" onClick={simulate}>Simulate Location Update (Delivery Partner)</button>
        <label><input type="checkbox" checked={poll} onChange={e=>setPoll(e.target.checked)}/> Polling every 5s</label>
      </div>
      {loc && <p>Latest: {loc.currentLocation} @ {loc.latitude}, {loc.longitude} ({loc.updatedAt})</p>}
      <div style={{height:400, marginTop:12, borderRadius:12, overflow:'hidden'}}>
        <MapContainer center={loc? [Number(loc.latitude), Number(loc.longitude)] : [13.0827,80.2707]} zoom={13} style={{height:'100%', width:'100%'}}>
          <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" attribution="&copy; OpenStreetMap"/>
          {loc && <Marker position={[Number(loc.latitude), Number(loc.longitude)]}><Popup>Delivery Partner</Popup></Marker>}
          <Marker position={[13.0843,80.2101]}><Popup>Customer</Popup></Marker>
        </MapContainer>
      </div>
      <p style={{fontSize:12, color:'#888', marginTop:8}}>Map via Leaflet + OpenStreetMap (no paid API). WebSocket ready: backend exposes /ws endpoint.</p>
    </div>
  )
}
