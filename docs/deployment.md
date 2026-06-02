# Deployment Guide

This project is split into three deployable pieces:

- Vue frontend
- Spring Boot backend
- PostgreSQL database

## Recommended Portfolio Deployment

Use hosted services that keep the architecture easy to explain:

- Frontend: Vercel, Netlify, or Render Static Site
- Backend: Render Web Service, Fly.io, Railway, or similar
- Database: Render PostgreSQL, Railway PostgreSQL, Neon, Supabase, or another managed PostgreSQL service

## Backend Environment Variables

```text
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<database>
SPRING_DATASOURCE_USERNAME=<database-user>
SPRING_DATASOURCE_PASSWORD=<database-password>
APP_CORS_ALLOWED_ORIGINS=https://<frontend-domain>
APP_DEMO_DATA_ENABLED=false
```

For a portfolio demo environment, `APP_DEMO_DATA_ENABLED=true` is acceptable if
the database is dedicated to demo use. For a real production environment, keep it
disabled and manage data intentionally.

## Frontend Environment Variables

The local Vite dev server uses a proxy for `/api`. In a hosted frontend, point
API calls at the backend domain if the platform does not provide a reverse proxy.

```text
VITE_API_BASE_URL=https://<backend-domain>
```

## Database Notes

The Docker demo stack uses:

```text
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

That is useful for local demos because the schema can initialize itself. For a
hosted production database, prefer schema migrations and keep the production
profile default:

```text
spring.jpa.hibernate.ddl-auto=validate
```

## Health Checks

Backend health endpoints:

```text
/actuator/health
/actuator/info
```

Use `/actuator/health` as the platform health check path for the backend.

## Deployment Checklist

1. Create a managed PostgreSQL database.
2. Deploy the backend with `SPRING_PROFILES_ACTIVE=prod`.
3. Set backend database credentials and `APP_CORS_ALLOWED_ORIGINS`.
4. Confirm `/actuator/health` returns `UP`.
5. Deploy the frontend.
6. Configure the frontend API base URL or platform proxy.
7. Exercise the demo flow from `docs/demo-script.md`.
