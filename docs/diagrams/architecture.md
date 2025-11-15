# System Architecture — Food Delivery and Live Tracking (Java track)

> Source: Mermaid below + export PNG via draw.io/Excalidraw for review. Keep this file + PNG in same folder.

```mermaid
flowchart LR
  subgraph Client["Client layer — Browser"]
    FE["React 18 + Vite<br/>Axios + React Router<br/>Leaflet map (polling 5s)<br/>Hosted: Vercel"]
  end
  subgraph Backend["Backend/API layer — Render"]
    SB["Spring Boot 3.2.5 Java 17<br/>Controllers (thin)<br/>Services (business logic)<br/>Spring Security + JWT<br/>WebSocket /ws (SockJS+STOMP)<br/>Swagger /swagger-ui.html<br/>Health /api/health"]
  end
  subgraph Data["Database layer"]
    PG[("PostgreSQL 15<br/>Railway managed<br/>food_delivery<br/>16 tables + FKs")]
    H2[("H2 mem<br/>local dev only")]
  end
  subgraph Ext["External / 3rd-party"]
    MAP["OpenStreetMap tiles<br/>(Leaflet, no key)"]
    PAY["Payment sandbox<br/>(abstraction: CASH/UPI/Card)"]
    OTP["OTP console log<br/>(dev-mode, future email/SMS)"]
  end
  FE -- "REST /api/** + Bearer JWT<br/>CORS strict" --> SB
  FE -- "WS /ws (ready)" --> SB
  SB -- "JPA/Hibernate<br/>DB_* env vars" --> PG
  SB -. "dev profile" .-> H2
  SB --> MAP
  SB --> PAY
  SB --> OTP
```

## Hosting boundary
| Piece | Platform | Env |
|-------|----------|-----|
| Frontend | Vercel | `VITE_API_URL=https://<backend>.onrender.com/api` |
| Backend | Render (Java, `mvn clean package`) | `SPRING_PROFILES_ACTIVE=prod`, `DB_*`, `JWT_SECRET`, `CORS_ALLOWED_ORIGINS` |
| DB | Railway PostgreSQL 15 | connection string → `DB_HOST/PORT/NAME/USER/PASSWORD` |

## Request flow
Browser → Vercel static → Render `/api/**` (JWT) → JPA → Railway Postgres → `{success,data,message}` JSON + correct HTTP codes.
