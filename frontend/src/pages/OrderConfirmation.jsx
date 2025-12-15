import { useParams, Link } from 'react-router-dom'
export default function OrderConfirmation(){
  const { id } = useParams()
  return (
    <div className="page" style={{textAlign:'center', paddingTop:40}}>
      <h2 style={{color:'#4caf50'}}>Order Placed! 🎉</h2>
      <p>Your order ID is <b>#{id}</b></p>
      <div className="order-steps" style={{maxWidth:700, margin:'24px auto'}}>
        {['PLACED','CONFIRMED','PREPARING','READY_FOR_PICKUP','PICKED_UP','OUT_FOR_DELIVERY','DELIVERED'].map(s=> <div key={s} className={`step ${s==='PLACED'?'active':''}`}>{s}</div>)}
      </div>
      <Link to={`/orders/${id}`} className="btn-primary">View Order</Link>
      <Link to={`/tracking/${id}`} className="btn-outline" style={{marginLeft:12}}>Track Order</Link>
    </div>
  )
}
