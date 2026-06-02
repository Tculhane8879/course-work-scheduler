# Demo Script

Use this flow when presenting the project to a recruiter, interviewer, or
portfolio reviewer.

## Local Docker Demo

```bash
cp .env.example .env
docker compose up --build
```

Open:

```text
http://localhost:5173
```

The Docker demo stack enables seed data by default, so the app starts with sample
courses, sections, availability blocks, and schedule preferences.

## Suggested Walkthrough

1. Open the course list and point out the mix of required and elective courses.
2. Open a course's sections and show that each section has instructor, modality,
   location, and meeting times.
3. Open constraints and show work blocks, unavailable time, and preferences.
4. Generate schedule options and compare ranked results.
5. Open the weekly calendar view to show how conflicts and gaps become visual.
6. Save one or two schedules.
7. Open saved schedules and compare the tradeoffs.

## Technical Talking Points

- Spring Boot exposes a REST API organized by domain.
- PostgreSQL persists courses, sections, constraints, preferences, and saved schedules.
- The schedule generator creates valid section combinations and rejects conflicts.
- The scoring layer ranks schedules based on user preferences.
- Vue presents the workflow as a multi-step scheduling tool.
- Docker Compose runs the frontend, backend, and database together.
- GitHub Actions runs backend tests, frontend linting, unit tests, and builds on PRs.
