# Problem Statement

## 1. Title
Food Delivery and Live Order Tracking Platform

## 2. Domain
Logistics / FoodTech — online food ordering with real-time delivery tracking

## 3. Who is the user? (2-3 user types, with roles)
- **CUSTOMER**: browses restaurants, searches food, manages cart/addresses, places orders, tracks live delivery, reviews/favorites.
- **RESTAURANT** (restaurant owner): manages menu items/categories, availability toggle, accepts/rejects orders, updates preparation status (`CONFIRMED` → `PREPARING` → `READY_FOR_PICKUP`).
- **DELIVERY_PARTNER**: views assigned deliveries, accepts pickup, updates status (`PICKED_UP` → `OUT_FOR_DELIVERY` → `DELIVERED`), posts GPS location.
- **ADMIN**: views platform stats, manages users/restaurants/orders/payments.

## 4. What problem are we solving? (3-5 sentences, real-life example)
Small restaurants struggle with phone-based ordering, no live visibility, and delivery disputes. Customers call to ask "where is my food?" with no answer. Example: a student orders lunch from Food Paradise at 12:30, the restaurant confirms but the delivery partner is stuck — nobody knows the ETA. This platform gives one flow: order → kitchen status → live map tracking → proof of delivery, reducing support calls and failed deliveries.

## 5. Proposed Solution (what the application will do, feature-wise)
- Auth with JWT + BCrypt + OTP email verification (console in dev), 4 roles with route guards.
- Restaurant discovery: search, filter, restaurant details + menu.
- Cart + addresses + checkout (CASH/UPI/Card abstraction) + order placement.
- Full order lifecycle: `PLACED` → `CONFIRMED` → `PREPARING` → `READY_FOR_PICKUP` → `PICKED_UP` → `OUT_FOR_DELIVERY` → `DELIVERED` + `CANCELLED`.
- Delivery module: assign partner, status updates, GPS location POST/GET, location history, Leaflet live map (5s polling, WebSocket `/ws` ready).
- Favorites, reviews/ratings, payment records, admin stats dashboard.
- Swagger UI at `/swagger-ui.html`, health at `/api/health`, consistent `{success, data, message}` responses.

## 6. Core Entities / Database Tables (list all, minimum 5)
1. `users` (id, full_name, email UNIQUE, phone, password_hash, role, enabled, email_verified)
2. `otp_tokens` (id, email, otp, expires_at, verified)
3. `restaurants` (id, owner_id FK→users, name, description, address, city, phone, email, image_url, status, rating)
4. `menu_categories` (id, restaurant_id FK, name, image_url)
5. `menu_items` (id, restaurant_id FK, category_id FK, name, description, price, image_url, available, rating)
6. `addresses` (id, user_id FK, label, address_line, city, pincode, lat, lng)
7. `carts` + `cart_items` (user→cart 1:1, cart→items 1:N, menu_item FK)
8. `orders` (id, customer_id FK, restaurant_id FK, total, fee, status, payment_status, payment_method, address)
9. `order_items` (order FK, menu_item FK, qty, price) — junction for Many-to-Many
10. `payments` (id, order_id FK UNIQUE, method, amount, txn_id, status)
11. `delivery_partners` (id, user_id FK UNIQUE, vehicle, available)
12. `deliveries` (id, order_id FK UNIQUE, partner_id FK, tracking_status, address)
13. `delivery_locations` (id, delivery_id FK, lat, lng, recorded_at) — history
14. `reviews` (id, user_id FK, restaurant_id FK, rating, comment)
15. `favorites` (id, user_id FK, restaurant_id FK NULL, menu_item_id FK NULL)
16. Total: 16 tables, real FK relationships, One-to-Many + One-to-One + Many-to-Many via `order_items`.

## 7. User Roles & Permissions (minimum 2 distinct roles, e.g. Admin & User)
| Role | Permissions |
|------|-------------|
| CUSTOMER | register/login/OTP, browse/search, cart, addresses, checkout, place/cancel own orders, track, favorites, reviews, profile |
| RESTAURANT | login, CRUD own menu items/categories, toggle availability, view incoming orders, accept/reject, update to READY_FOR_PICKUP |
| DELIVERY_PARTNER | login, view assigned deliveries, accept, view pickup/customer address, update PICKED_UP/OUT_FOR_DELIVERY/DELIVERED, post location |
| ADMIN | login, GET /api/admin/stats, manage users/restaurants/orders, view all orders/payments |

Enforced via Spring Security `@EnableMethodSecurity` + JWT roles + frontend `ProtectedRoute` guards.

## 8. Success Criteria (e.g. 'a user should be able to book an appointment in under 1 minute')
- A new customer can register → verify OTP → login and receive a real JWT in under 2 minutes.
- A customer can search food → add to cart → checkout → place order in under 2 minutes.
- Restaurant can accept an order and move it to READY_FOR_PICKUP; delivery partner can complete to DELIVERED with location history visible on Leaflet map.
- All service-layer unit tests pass (`mvn clean verify` green); CI fails on test failure.
- Backend live on Render + frontend live on Vercel + Postgres on Railway, all via env vars, Swagger live.

## 9. Out of Scope (clearly list what you will NOT build, to avoid over-commitment)
- Real payment gateway charge (Stripe/Razorpay live capture) — COD works, UPI/Card are recorded abstractions.
- Real SMS/email delivery — OTP logged to console in dev mode (`app.otp.dev-mode=true`).
- File upload storage (S3) — image is URL string only.
- Native mobile apps, AI recommendation engine (deferred to Review-III enhancement).
- Multi-restaurant single-cart split billing.

## 10. Chosen Track: Java (Spring Boot) / Python (Django or FastAPI)
Chosen Track: Java (Spring Boot 3.x, Java 17) — Spring Security + JWT (jjwt), Spring Data JPA + Hibernate, PostgreSQL 15 (Railway) / H2 (local dev), Maven, JUnit 5, springdoc-openapi Swagger, React 18 + Vite frontend, GitHub Actions CI/CD, Render (backend) + Vercel (frontend).
