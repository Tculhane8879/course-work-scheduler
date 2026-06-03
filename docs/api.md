# API Documentation

The backend publishes interactive OpenAPI documentation with Springdoc.

## Local URLs

When the backend is running locally or through Docker Compose, open:

```text
http://localhost:8080/swagger-ui.html
```

The raw OpenAPI JSON is available at:

```text
http://localhost:8080/v3/api-docs
```

## Endpoint Groups

The API is organized around the same workflow as the frontend.

| Area | Endpoint Pattern | Purpose |
| --- | --- | --- |
| Health | `/api/health` | Simple application health response |
| Courses | `/api/courses` | Create, list, update, and delete courses |
| Sections | `/api/courses/{courseId}/sections`, `/api/sections/{sectionId}` | Manage course sections and meeting times |
| Availability | `/api/availability-blocks` | Manage work blocks and unavailable time |
| Preferences | `/api/preferences` | Read and update schedule scoring preferences |
| Schedule generation | `/api/schedules/generate` | Generate ranked valid schedules |
| Saved schedules | `/api/saved-schedules` | Save, list, prefer, and delete schedule snapshots |

## Configuration

OpenAPI and Swagger UI are enabled by default for local development:

```text
SPRINGDOC_API_DOCS_ENABLED=true
SPRINGDOC_SWAGGER_UI_ENABLED=true
```

The production Spring profile disables both by default. For a hosted portfolio
demo, enable them intentionally with environment variables if you want reviewers
to inspect the API contract.

## Reviewer Flow

1. Start the app with `docker compose up --build`.
2. Open the frontend at `http://localhost:5173`.
3. Open Swagger UI at `http://localhost:8080/swagger-ui.html`.
4. Compare the frontend workflow to the backend endpoint groups.
5. Use `/v3/api-docs` if you want to inspect or export the generated OpenAPI
   contract.
