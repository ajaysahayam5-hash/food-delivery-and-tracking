import { useEffect, useState } from 'react'
import { useAuth } from '../context/AuthContext'
import api from '../services/api'
import { getDeliveryByOrder, updateDeliveryStatus, postLocation, getLocation } from '../services/api'

export default function DeliveryDashboard(){
  const { user } = useAuth()
  const [deliveries, setDeliveries] = useState([])
  const [partnerId, setPartnerId] = useState(null)

  const load = async () => {
    try {
      if (!partnerId) {
        const { data: partner } = await api.get('/delivery-partners/user')
        setPartnerId(partner.id)
      }
      const { data } = await api.get(`/deliveries/partner/${partnerId}`)
      setDeliveries(data)
    } catch (e) {
      console.error('Failed to load deliveries', e)
      setDeliveries([])
    }
  }

  useEffect(() => { load() }, [])

  const updateStatus = async (deliveryId, status) => {
    try {
      await updateDeliveryStatus(deliveryId, status)
      load()
    } catch (e) {
      console.error('Failed to update status', e)
    }
  }

  return (
    <div className="page">
      <h2>Delivery Partner Dashboard</h2>
      <p style={{color:'#666', marginBottom:16}}>Logged in as {user?.fullName} ({user?.email})</p>
      {deliveries.map(d => (
        <div key={d.id} className="card" style={{padding:12, marginBottom:8}}>
          <div style={{display:'flex', justifyContent:'space-between', alignItems:'center', flexWrap:'wrap', gap:8}}>
            <div>
              <b>Order #{d.orderId}</b> - {d.trackingStatus} - {d.deliveryAddress}
            </div>
            <div style={{display:'flex', gap:8, flexWrap:'wrap'}}>
              {d.trackingStatus === 'Assigned' && (
                <button className="btn-primary" onClick={() => updateStatus(d.id, 'PICKED_UP')}>Picked Up</button>
              )}
              {d.trackingStatus === 'PICKED_UP' && (
                <button className="btn-primary" onClick={() => updateStatus(d.id, 'OUT_FOR_DELIVERY')}>Out for Delivery</button>
              )}
              {d.trackingStatus === 'OUT_FOR_DELIVERY' && (
                <button className="btn-primary" onClick={() => updateStatus(d.id, 'DELIVERED')}>Delivered</button>
              )}
            </div>
          </div>
        </div>
      ))}
      {deliveries.length === 0 && <p style={{color:'#888', marginTop:16}}>No assigned deliveries.</p>}
    </div>
  )
}