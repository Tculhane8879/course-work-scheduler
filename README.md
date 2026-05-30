# Course + Work Schedule Optimizer

A full-stack web application that helps students generate optimized course and work schedules based on class sections, work availability, and personal preferences.

## Tech Stack

- Vue 3
- Spring Boot
- PostgreSQL
- Docker
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
mvn spring-boot:run
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

## Current Features

- Spring Boot backend setup
- Vue frontend setup
- PostgreSQL database setup
- Health check REST endpoint

## Planned Features

- Course management
- Section management
- Work availability management
- Schedule generation
- Schedule scoring
- Calendar UI
- Cloud deployment