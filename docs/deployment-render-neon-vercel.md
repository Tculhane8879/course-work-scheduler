# Render + Neon + Vercel Deployment

This is the recommended hosted portfolio deployment path for this project.

## Live URLs

```text
Frontend: https://course-work-scheduler.vercel.app
Backend:  https://course-work-scheduler-api.onrender.com
Swagger:  https://course-work-scheduler-api.onrender.com/swagger-ui.html
Health:   https://course-work-scheduler-api.onrender.com/actuator/health
```

The backend runs on Render's free tier, so the first request after a quiet
period can be slow while the service wakes up.

## Stack

```text
Frontend: Vercel
Backend: Render
Database: Neon Postgres
```

This keeps the architecture close to the local Docker setup while using managed
services that are practical for a small portfolio demo.

## 1. Create The Neon Database

1. Create a Neon project.
2. Create or use the default database.
3. Copy the pooled or direct connection details.

The backend needs JDBC-style values:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://<neon-host>/<database>?sslmode=require
SPRING_DATASOURCE_USERNAME=<neon-user>
SPRING_DATASOURCE_PASSWORD=<neon-password>
```

If Neon shows a connection string that starts with `postgresql://` or
`postgres://`, convert it to the `jdbc:postgresql://` format above before adding
it to Render.

## 2. Deploy The Backend On Render

Use the root `render.yaml` blueprint or create a Render Web Service manually.

The blueprint deploys:

```text
course-work-scheduler-api
```

from:

```text
backend/Dockerfile
```

Set these Render environment variables when prompted:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://<neon-host>/<database>?sslmode=require
SPRING_DATASOURCE_USERNAME=<neon-user>
SPRING_DATASOURCE_PASSWORD=<neon-password>
APP_CORS_ALLOWED_ORIGINS=https://<vercel-frontend-domain>
```

For the first backend deploy, the Vercel frontend URL will not exist yet. Use a
temporary placeholder such as:

```text
APP_CORS_ALLOWED_ORIGINS=https://replace-after-vercel-deploy.example
```

After the frontend deploys, replace it with the exact Vercel production origin.

The blueprint sets these portfolio-demo defaults:

```text
APP_DEMO_DATA_ENABLED=true
SPRINGDOC_API_DOCS_ENABLED=true
SPRINGDOC_SWAGGER_UI_ENABLED=true
SPRING_FLYWAY_BASELINE_ON_MIGRATE=false
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
```

Render health check path:

```text
/actuator/health
```

## 3. Deploy The Frontend On Vercel

Import the GitHub repository into Vercel as a new project.

Use these project settings:

```text
Root Directory: frontend
Framework Preset: Vite
Build Command: npm run build
Output Directory: dist
Install Command: npm ci
```

Set this Vercel environment variable:

```text
VITE_API_BASE_URL=https://<render-backend-domain>
```

The frontend includes `frontend/vercel.json` so direct visits to routes like
`/constraints`, `/schedules`, and `/saved-schedules` resolve to the Vue app.

## 4. Update Backend CORS

After Vercel gives you the production frontend URL, update Render:

```text
APP_CORS_ALLOWED_ORIGINS=https://<vercel-frontend-domain>
```

The current hosted demo uses:

```text
APP_CORS_ALLOWED_ORIGINS=https://course-work-scheduler.vercel.app
```

Redeploy or restart the Render backend after changing the environment variable.

## 5. Verify The Hosted Demo

Check the backend first:

```text
https://<render-backend-domain>/actuator/health
https://<render-backend-domain>/api/health
https://<render-backend-domain>/swagger-ui.html
https://<render-backend-domain>/v3/api-docs
```

Then check the frontend:

```text
https://<vercel-frontend-domain>
https://<vercel-frontend-domain>/constraints
https://<vercel-frontend-domain>/schedules
https://<vercel-frontend-domain>/saved-schedules
```

Finally, exercise the demo flow:

1. Confirm seeded courses appear.
2. Confirm constraints and preferences load.
3. Generate schedules.
4. Save a schedule.
5. Mark a saved schedule as preferred.

## Free-Tier Notes

- Render free web services can spin down after inactivity. The first request
  after a quiet period can be slow.
- Neon free databases are appropriate for small, intermittent portfolio demos,
  but watch usage limits in the Neon dashboard.
- Vercel Hobby is intended for personal, non-commercial projects. Watch usage
  limits in the Vercel dashboard.
- If this becomes a production app or gets meaningful traffic, move to paid
  tiers and disable public Swagger UI.
