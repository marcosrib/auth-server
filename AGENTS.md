# AGENTS.md

## Commands
- Use the Gradle wrapper: `./gradlew bootRun`, `./gradlew test`, `./gradlew build`.
- Focus a single test with Gradle's test filter, e.g. `./gradlew test --tests 'com.br.auth_server.AuthServerApplicationTests'`.
- `./gradlew tasks --all` is quick and shows the full local task surface; there is no separate lint/format/typecheck setup in this repo.

## Runtime Prerequisites
- The app is configured only for MySQL; there is no embedded test database or alternate local profile checked in.
- Start MySQL with `docker compose up -d mysql` before `bootRun` or full-context tests.
- Default local DB settings live in `src/main/resources/application.yml`: database `auth_server_db`, user `user`, password `user123`, server port `9000`.

## Persistence And Test Data
- Flyway runs from two locations on startup: `classpath:db/migration` and `classpath:db/testdata`.
- `src/main/resources/db/testdata/afterMigrate.sql` deletes and reseeds `auth_user` on each migration run, so local startup is not data-preserving for that table.
- OAuth2 authorization data is stored in MySQL via JDBC services from `src/main/java/com/br/auth_server/config/PersistenceConfig.java`; schema comes from Flyway migrations `V1` and `V2`.

## Code Layout
- This is a single-module Spring Boot app; the entrypoint is `src/main/java/com/br/auth_server/AuthServerApplication.java`.
- There is no `SecurityConfig.java` or `AuthorizationServerConfig.java` in the current tree; auth-server behavior is driven mainly by `application.yml` plus beans under `src/main/java/com/br/auth_server/security/` and `config/`.
- User login and OIDC user info both read from `AuthUserRepository`; seeded users live in `db/testdata/afterMigrate.sql`.

## Repo-Specific Gotchas
- The Java package uses underscores: `com.br.auth_server`. `HELP.md` notes the dashed package name was invalid.
- `application.yml` currently enables `org.springframework.security: TRACE`, so local runs are intentionally very noisy.
- The only checked-in test is `AuthServerApplicationTests`, a `@SpringBootTest`; treat `./gradlew test` as an integration-style verification step, not a fast unit-test pass.
