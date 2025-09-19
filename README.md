# Food Delivery and Live Order Tracking
> Order food online with real-time kitchen status and live delivery map — Java (Spring Boot) + React capstone.

## 2. Live Demo + Video Demo
- Frontend (Vercel): `https://<your-frontend>.vercel.app` — set after deploy (see Deployment)
- Backend (Render): `https://<your-backend>.onrender.com` — health: `/api/health`, Swagger: `/swagger-ui.html`
- Video demo (2–4 min, Day 41+): add Loom/YouTube link here after recording
- Local demo: frontend `http://localhost:3000`, backend `http://localhost:8080`

## 3. Overview
Small restaurants lose orders to phone chaos with zero delivery visibility. This app gives one flow: customer browses restaurants → cart → checkout → order, restaurant confirms and prepares, delivery partner picks up with GPS pings, customer watches live Leaflet map to `DELIVERED`. Four roles (Customer, Restaurant, Delivery Partner, Admin) with JWT + OTP verification and a full `PLACED→DELIVERED+CANCELLED` lifecycle.

## 4. Architecture Diagram
See `docs/diagrams/architecture.md` (Mermaid source + hosting boundary). Export PNG via draw.io/Excalidraw as `docs/diagrams/architecture.png` before review.

```
Browser (React/Vite on Vercel) --REST /api + Bearer JWT--> Spring Boot (Render) --JPA--> PostgreSQL 15 (Railway)
                                      --WS /ws (ready)-->  + Swagger + /api/health + Leaflet/OSM + COD/UPI/Card abstraction
```

## 5. Tech Stack
| Layer | Choice |
|-------|--------|
| Frontend | React 18, Vite 5, React Router v6, Axios, Leaflet + react-leaflet (OpenStreetMap) |
| Backend | Spring Boot 3.2.5, Java 17, Web, Data JPA + Hibernate, Security + JWT (jjwt 0.12.5), Validation, WebSocket, springdoc-openapi 2.5.0 |
| ORM | Spring Data JPA (no raw SQL) |
| Database | PostgreSQL 15 (Railway prod) / H2 mem (local dev) |
| Build | Maven (`mvn clean verify`), npm (`npm run build`) |
| Testing | JUnit 5 + Mockito (service layer, 27 tests green) |
| API Docs | springdoc-openapi Swagger UI |
| CI/CD | GitHub Actions (`.github/workflows/backend.yml`, `frontend.yml`) |
| Hosting | Render (backend) + Vercel (frontend) + Railway Postgres |
| Container | Docker (bonus): Java multi-stage `backend/Dockerfile`, `docker-compose.yml` |

## 6. Features
- **Auth**: register/login/logout, BCrypt, JWT, OTP generate/verify/resend (logger in dev), 4 roles + guards.
- **Customer**: search restaurants/food, restaurant details + menu, cart add/update/remove/clear, addresses CRUD, checkout CASH/UPI/Card, place/cancel own orders, my orders + details, live tracking (5s polling + simulate location), favorites (wired UI), reviews, profile.
- **Restaurant (`/restaurant/dashboard`)**: menu item CRUD, availability toggle, incoming orders, accept/reject, `CONFIRMED→PREPARING→READY_FOR_PICKUP`.
- **Delivery (`/delivery/dashboard`)**: assigned list, accept, pickup/customer addresses, `PICKED_UP→OUT_FOR_DELIVERY→DELIVERED`, `POST /api/deliveries/{id}/location`.
- **Admin (`/admin`)**: stats (customers/restaurants/orders/payments/partners), users + restaurants lists.
- **Platform**: consistent `{success,data,message}` + codes 200/201/400/401/404/500, `@Valid` + service guards, strict CORS from env, `/api/health`, SLF4J logging, Swagger.

## 7. Screenshots
Add after local run (store in `docs/screenshots/` and embed):
- Home + search, Restaurant details + menu, Cart + Checkout, Tracking map, Restaurant dashboard, Delivery dashboard, Admin stats, Swagger UI.

## 8. Getting Started
Prerequisites: Java 17, Maven 3.9+, Node 20+, PostgreSQL 15 (or H2 dev default), Git.
```bash
git clone <your-repo-url>
cd "food delivery"
# Backend (dev H2, port 8080)
cd backend
mvn spring-boot:run
# Frontend (port 3000) in new terminal
cd ../frontend
npm install
npm run dev -- --host 0.0.0.0 --port 3000
```
Open frontend `http://localhost:3000`, backend health `http://localhost:8080/api/health`, Swagger `http://localhost:8080/swagger-ui.html`, H2 console `http://localhost:8080/h2-console` (JDBC `jdbc:h2:mem:food_delivery`, user `sa`).
Prod local with Postgres: set `SPRING_PROFILES_ACTIVE=prod` + `DB_*` env (see below), then `mvn spring-boot:run`.

## 9. Environment Variables
| Name | Description | Required |
|------|-------------|----------|
| PORT | Backend port | N (default 8080) |
| SPRING_PROFILES_ACTIVE | `dev` (H2) or `prod` (Postgres) | N (default dev) |
| DB_HOST / DB_PORT / DB_NAME / DB_USER / DB_PASSWORD | PostgreSQL connection (Railway in prod) | Y in prod |
| JWT_SECRET | 256-bit min-32-char secret | Y in prod |
| JWT_EXPIRATION | ms (default 86400000) | N |
| CORS_ALLOWED_ORIGINS | comma-separated frontend origins (localhost + Vercel URL) | Y in prod |
| APP_OTP_DEV_MODE | true logs OTP via logger (dev), false in prod | N |
| VITE_API_URL | Frontend API base, e.g. `http://localhost:8080/api` or Render URL + `/api` | Y |
| RENDER_DEPLOY_HOOK | GitHub Secret: Render deploy hook URL | Y for CD |
| VERCEL_TOKEN | GitHub Secret: Vercel token | Y for CD |
See `.env.example` (root), `backend/.env.example`, `frontend/.env.example`. Never commit real `.env`.

## 10. API Documentation
- Local Swagger: `http://localhost:8080/swagger-ui.html`, JSON: `/v3/api-docs`
- Prod Swagger: `https://<your-backend>.onrender.com/swagger-ui.html`
- Contract summary: `docs/diagrams/api-contract.md`; ER: `docs/diagrams/er.dbml` + `er.md`; classes: `class-diagram.md`.

## 11. Running Tests
```bash
cd backend
mvn -B clean verify
# single: mvn -B -Dtest=OrderServiceTest test
```
27 JUnit5 + Mockito service tests (Auth 7, Order 7, Cart 4, Restaurant 4, Delivery 5) — target ≥40% service methods, 1 class per major module, zero failures (CI fails on red).

## 12. Deployment
- **DB (Railway)**: create PostgreSQL 15, copy host/port/db/user/password into Render env as `DB_*`.
- **Backend (Render)**: repo + `backend/render.yaml` (Java `mvn clean package`, `java -jar target/*.jar`, health `/api/health`), env `SPRING_PROFILES_ACTIVE=prod`, `DB_*`, `JWT_SECRET`, `CORS_ALLOWED_ORIGINS=https://<frontend>.vercel.app`, `APP_OTP_DEV_MODE=false`. Auto-deploy via `RENDER_DEPLOY_HOOK` secret from `backend.yml`.
- **Frontend (Vercel)**: root `frontend/`, `vercel.json` (Vite build → `dist`), env `VITE_API_URL=https://<backend>.onrender.com/api`. Auto-deploy via `VERCEL_TOKEN` from `frontend.yml`.
- **CI/CD**: push/PR → checkout → install (Maven/npm) → `mvn clean verify` / `npm run build` (fail on test/build error) → on `main` push trigger Render hook / Vercel prod deploy.
- Docker bonus: `docker-compose.yml` (postgres + backend Java + frontend nginx).

## 13. Folder Structure
```
.
├── Problem_Statement.md  CHANGELOG.md  LICENSE  .env.example  README.md
├── .github/workflows/backend.yml  frontend.yml
├── docs/diagrams/architecture.md  er.dbml  er.md  class-diagram.md  api-contract.md
├── backend/pom.xml  Dockerfile  render.yaml  .env.example
│   └── src/main/java/com/fooddelivery/{controller,service,repository,entity,dto,security,config,exception}
│   └── src/main/resources/application{,.dev,.prod}.properties
│   └── src/test/java/.../service/*Test.java
├── frontend/package.json  vite.config.js  vercel.json  Dockerfile
│   └── src/{pages,components,services/api.js,context,hooks}
└── database/schema.sql  sample_data.sql  schema_postgresql.sql
```

## 14. Future Enhancements
- Review-III AI: ETA prediction / recommendations (proposal by Day 43).
- Stripe/Razorpay sandbox capture (replace abstraction), STOMP live push (replace polling), S3 image upload, Cypress/Selenium e2e, structured logging.

## 15. License
MIT — see `LICENSE`.

## 16. Author / Contact
Java Batch capstone — Food Delivery and Live Tracking. GitHub: add repo URL + email here.
