# API Contract — auto-generated Swagger (springdoc-openapi)

- Local: `http://localhost:8080/swagger-ui.html` and `http://localhost:8080/v3/api-docs`
- Prod (after Render deploy): `https://<your-backend>.onrender.com/swagger-ui.html`
- All paths under `/api/**`, JSON `{success, data, message}` (see `dto/ApiResponse.java`), codes 200/201/400/401/404/500.

## Endpoints (grouped)
```
POST /api/auth/register, /login, /verify-otp, /resend-otp, /logout
GET  /api/health
GET  /api/restaurants?search=  GET /api/restaurants/{id}  POST/PUT/DELETE /api/restaurants[/{id}]
GET  /api/menu-items?search=&restaurantId=  GET /api/menu-items/{id}  GET /api/menu-items/restaurant/{rid}  POST/PUT/DELETE
GET/POST/PUT/DELETE /api/categories[/{id}]
GET  /api/cart  POST /api/cart/items  PUT /api/cart/items/{id}  DELETE /api/cart/items/{id}  DELETE /api/cart
POST /api/orders  GET /api/orders  GET /api/orders/all  GET /api/orders/{id}  PUT /api/orders/{id}/status  POST /api/orders/{id}/cancel  GET /api/orders/restaurant/{rid}
GET  /api/deliveries/{id}  GET /api/deliveries/order/{orderId}  PUT /api/deliveries/{id}/status  POST /api/deliveries/{id}/location  GET .../location  GET .../history  POST .../assign/{partnerId}  GET /api/deliveries/partner/{partnerId}  GET /api/deliveries
GET/POST/PUT/DELETE /api/addresses[/{id}]
POST /api/payments  GET /api/payments/{id}  GET /api/payments/order/{orderId}
POST /api/reviews  GET /api/reviews/restaurant/{id}  GET /api/reviews
GET/POST/DELETE /api/favorites[/{id}]
GET /api/users/me  GET /api/users  GET /api/users/{id}  PUT /api/users/{id}
GET /api/delivery-partners/user  GET /api/delivery-partners  GET /api/delivery-partners/{id}  POST/PUT
GET /api/admin/stats (ADMIN)
WS  /ws (SockJS+STOMP, broker /topic, prefix /app)
```
Auth: `Authorization: Bearer <JWT>` (frontend Axios auto-attaches, 401 → `/login`).
