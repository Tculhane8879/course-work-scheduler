# Database Migrations

The backend uses Flyway to manage PostgreSQL schema changes.

## Migration Location

```text
backend/src/main/resources/db/migration/
```

Flyway runs migrations in version order. The initial schema lives in:

```text
V1__create_initial_schema.sql
```

## Runtime Behavior

Spring Boot runs Flyway during backend startup before Hibernate validates the
entity mappings.

The intended production behavior is:

```text
Flyway creates or updates schema
Hibernate validates entity/schema compatibility
Application starts only if validation passes
```

## Local And Docker Demo Behavior

Local and Docker demo runs use `SPRING_FLYWAY_BASELINE_ON_MIGRATE=true` by
default. This allows an existing local database from earlier development to be
baselined instead of failing because tables already exist without Flyway history.

For a brand-new database, Flyway applies `V1__create_initial_schema.sql`.

## Production Guidance

For a fresh hosted PostgreSQL database:

```text
SPRING_PROFILES_ACTIVE=prod
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_FLYWAY_BASELINE_ON_MIGRATE=false
```

For a non-empty existing production database that was not previously managed by
Flyway, create a backup and baseline intentionally before deployment.

## Adding Future Migrations

Create a new SQL file with the next version number:

```text
V2__describe_change.sql
V3__describe_next_change.sql
```

Never edit a migration that has already been applied to a shared or hosted
database. Add a new migration instead.
