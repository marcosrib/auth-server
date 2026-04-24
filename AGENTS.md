# AGENTS.md

## Build & Run
```bash
./gradlew bootRun    # Run application
./gradlew test      # Run tests
./gradlew build     # Build JAR
```

## Tech Stack
- Spring Boot 4.0.6
- Spring Authorization Server (OAuth2)
- Spring Security 7.x
- Java 25

## Key Files
- `src/main/resources/application.yml` - Server config (port 9000, scopes)
- `src/main/java/.../config/SecurityConfig.java` - Web security
- `src/main/java/.../config/AuthorizationServerConfig.java` - OAuth2 server config

## Configuration
- Server runs on `http://localhost:9000`
- Default scopes: `openid`, `profile`, `email`, `read`, `write`

## Gotchas
- Spring Security 7.x uses new `.oauth2AuthorizationServer()` DSLinstead of deprecated `OAuth2AuthorizationServerConfiguration.applyDefaultSecurity()`
- `SecurityFilterChain` order matters: `@Order(1)` for OAuth2, `@Order(2)` for default
- `.securityMatcher()` required to isolate OAuth2 endpoints