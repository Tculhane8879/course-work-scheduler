# Course + Work Schedule Optimizer

A full-stack web application that helps students generate optimized course and work schedules based on class sections, work availability, and personal preferences.

## Tech Stack

- Vue 3
- Spring Boot
- PostgreSQL
- Docker
- Vite
- Vitest
- GitHub Actions, planned
- Cloud deployment, planned

## Project Structure

```text
course-work-scheduler/
  backend/
  frontend/
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

## Current Features

- Spring Boot backend setup
- Vue frontend setup
- PostgreSQL database setup
- Health check REST endpoint
- Course CRUD REST API
- Course management UI
- Section and meeting-time REST API
- Section management UI
- Work availability and constraints REST API
- Schedule preference management UI
- Basic backend validation and error responses
- Local frontend `/api` proxy setup

## Planned Features

- Schedule generation
- Schedule scoring
- Calendar UI
- Cloud deployment
