# Changelog

## [1.1.0] - Review-II (Day 41) - Full Product Live (Java track)
- Java-only backend: removed parallel Node/Express shadow app (`backend/src/server.js`, `routes/`, `controllers/`, `middleware/`, `config/`, `package.json`) to meet standard folder structure.
- Added springdoc-openapi Swagger UI (`/swagger-ui.html`, `/v3/api-docs`), `HealthController` (`GET /api/health`), consistent `ApiResponse{success,data,message}` + `GlobalExceptionHandler`.
- Hardened security: BCrypt, JPA-only queries, `@Valid` validation, env-driven CORS (no `*`), JWT via env, removed `System.out` OTP leaks → SLF4J logger.
- Switched prod to PostgreSQL 15 (Railway) via `DB_*` env vars; dev stays H2; fixed `backend/Dockerfile` to multi-stage Maven build; added `render.yaml`, `frontend/vercel.json`.
- Implemented Favorites UI (`/favorites` wired to `/api/favorites`); fixed delivery `GET /` gap; standardized HTTP codes 200/201/400/401/404/500.
- Added JUnit 5 service tests (Auth, Order, Cart, Restaurant + others, >=40% service methods) + GitHub Actions `backend.yml` + `frontend.yml` (lint → test → deploy hooks).
- Docs: `Problem_Statement.md`, `docs/diagrams/` (architecture, ER `.dbml`, class diagram, API contract), README v2 (16-section spec), `.env.example` at root + backend + frontend.

## [1.0.0] - Review-I (Day 11) - MVP
- JWT + BCrypt + OTP auth, 4 roles, restaurant/menu/category CRUD, cart, full order lifecycle, delivery location/history, addresses, reviews, favorites backend, admin stats, Leaflet polling map, H2 dev + MySQL/Postgres schemas, Jar + dist built.

## [Unreleased] - Review-III ideas
- AI enhancement (e.g., ETA prediction / recommendation), Stripe/Razorpay sandbox capture, STOMP live tracking (replace polling), S3 image upload.
