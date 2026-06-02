# Course + Work Schedule Optimizer

A full-stack web application that helps students generate optimized course and work schedules based on class sections, work availability, and personal preferences.

## Tech Stack

- Vue 3
- Spring Boot
- PostgreSQL
- Docker
- Vite
- Vitest
- GitHub Actions
- Cloud deployment, planned

## Project Structure

```text
course-work-scheduler/
  backend/
  frontend/
  .github/workflows/
  docker-compose.yml
```

## Local Development

### Start PostgreSQL

```bash
docker compose up -d
```

### Start Backend

```bash
cd backend
./mvnw spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

Health check:

```text
http://localhost:8080/api/health
```

Actuator health check:

```text
http://localhost:8080/actuator/health
```

### Start Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend runs at:

```text
http://localhost:5173
```

During local development, the Vite dev server proxies `/api` requests to the
Spring Boot backend at `http://localhost:8080`.

## Configuration

Example environment files are included for local setup:

```text
backend/.env.example
frontend/.env.example
```

The backend also provides local defaults in `application.properties`, so Docker
Compose plus `./mvnw spring-boot:run` works without extra configuration.

The backend uses Spring profiles:

```text
local  default profile for local development
prod   production-oriented profile for Docker/deployment
```

Production settings are defined in:

```text
backend/src/main/resources/application-prod.properties
```

The root `.env.example` file documents the environment variables used by Docker
Compose.

## Production-like Docker Stack

To run PostgreSQL, the Spring Boot API, and the built Vue frontend together:

```bash
cp .env.example .env
docker compose up --build
```

Frontend:

```text
http://localhost:5173
```

Backend:

```text
http://localhost:8080
```

The frontend container serves the compiled Vue app with Nginx and proxies `/api`
requests to the backend container.

The Compose stack sets `SPRING_JPA_HIBERNATE_DDL_AUTO=update` so a fresh local
demo database can initialize itself. For a hosted production database, use a
migration tool and keep the production profile default of `validate`.

## Tests

### Backend

```bash
cd backend
./mvnw test
```

Backend tests use an in-memory H2 database so they can run without starting
PostgreSQL.

### Frontend

```bash
cd frontend
npm run test:unit -- --run
npm run build
```

### CI

GitHub Actions runs backend tests, frontend linting, frontend unit tests, and the
frontend production build on pull requests and pushes to `develop` or `main`.

## Current Features

- Spring Boot backend setup
- Vue frontend setup
- PostgreSQL database setup
- Health check REST endpoint
- Actuator health/info endpoints
- Course CRUD REST API
- Course management UI
- Section and meeting-time REST API
- Section management UI
- Work availability and constraints REST API
- Schedule preference management UI
- Constraint-based schedule generation API
- Ranked schedule generation UI
- Schedule scoring
- Weekly calendar visualization for generated schedules
- Saved schedule snapshots
- Saved schedule comparison UI
- Basic backend validation and error responses
- Local frontend `/api` proxy setup
- GitHub Actions CI workflow
- Dockerized backend and frontend services

## Planned Features

- Cloud deployment
