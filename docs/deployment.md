# Deployment Guide

This project is split into three deployable pieces:

- Vue frontend
- Spring Boot backend
- PostgreSQL database

## Live Portfolio Demo

```text
Frontend: https://course-work-scheduler.vercel.app
Backend:  https://course-work-scheduler-api.onrender.com
Swagger:  https://course-work-scheduler-api.onrender.com/swagger-ui.html
Health:   https://course-work-scheduler-api.onrender.com/actuator/health
```

The backend runs on Render's free tier, so the first request after a quiet
period can be slow while the service wakes up.

## Recommended Portfolio Deployment

Use hosted services that keep the architecture easy to explain:

- Frontend: Vercel, Netlify, or Render Static Site
- Backend: Render Web Service, Fly.io, Railway, or similar
- Database: Render PostgreSQL, Railway PostgreSQL, Neon, Supabase, or another managed PostgreSQL service

The recommended path for this portfolio project is:

```text
Frontend: Vercel
Backend: Render
Database: Neon Postgres
```

The step-by-step guide lives in:

```text
docs/deployment-render-neon-vercel.md
```

For the simplest portfolio demo story, deploy in this order:

1. Managed PostgreSQL database
2. Spring Boot backend
3. Vue frontend

This order gives the backend a database URL before startup, then gives the
frontend a stable backend URL for API calls.

## Backend Environment Variables

```text
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=10000
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<database>
SPRING_DATASOURCE_USERNAME=<database-user>
SPRING_DATASOURCE_PASSWORD=<database-password>
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_FLYWAY_BASELINE_ON_MIGRATE=false
APP_CORS_ALLOWED_ORIGINS=https://<frontend-domain>
APP_DEMO_DATA_ENABLED=false
SPRINGDOC_API_DOCS_ENABLED=false
SPRINGDOC_SWAGGER_UI_ENABLED=false
```

For a portfolio demo environment, `APP_DEMO_DATA_ENABLED=true` is acceptable if
the database is dedicated to demo use. For a real production environment, keep it
disabled and manage data intentionally.

For a public portfolio demo, enabling Swagger UI can be useful:

```text
SPRINGDOC_API_DOCS_ENABLED=true
SPRINGDOC_SWAGGER_UI_ENABLED=true
```

Disable those again before treating the app as a real production deployment.

## Frontend Environment Variables

The local Vite dev server uses a proxy for `/api`. In a hosted frontend, point
API calls at the backend domain if the platform does not provide a reverse proxy.

```text
VITE_API_BASE_URL=https://<backend-domain>
```

For Vercel, Netlify, and most static hosts, set this as a frontend environment
variable before building. For Docker-based frontend hosting, pass it as the
`VITE_API_BASE_URL` build argument.

If the frontend and backend are served behind the same domain or reverse proxy,
leave `VITE_API_BASE_URL` empty so the frontend calls relative `/api` paths.

## Hosted Demo Verification

After deployment, verify these URLs in order:

```text
https://<backend-domain>/actuator/health
https://<backend-domain>/api/health
https://<backend-domain>/swagger-ui.html
https://<frontend-domain>
```

Then exercise the portfolio walkthrough from `docs/demo-script.md`.

If the frontend loads but API calls fail, check:

- `VITE_API_BASE_URL` points to the deployed backend origin.
- `APP_CORS_ALLOWED_ORIGINS` includes the exact frontend origin.
- The backend health check is passing.
- The database credentials are set on the backend service.

## Database Notes

Flyway manages schema creation and future schema changes. Hibernate validates the
schema at startup:

```text
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
```

Migration files live in:

```text
backend/src/main/resources/db/migration/
```

The Docker demo stack sets `SPRING_FLYWAY_BASELINE_ON_MIGRATE=true` by default
so existing local development volumes can start cleanly. For a fresh hosted
production database, keep it `false` and let Flyway apply migrations normally.

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
4. Keep `SPRING_JPA_HIBERNATE_DDL_AUTO=validate`.
5. Confirm Flyway runs successfully in the backend startup logs.
6. Confirm `/actuator/health` returns `UP`.
7. Deploy the frontend.
8. Configure the frontend API base URL or platform proxy.
9. Exercise the demo flow from `docs/demo-script.md`.
