# Kotlin Rest Template

Web application template based on spring boot 3 and Kotlin, Gradle, Spring Boot 3, Hibernate, Postgres, Flyway,
TestContainers, RestAssured, ExceptionHandler
Swagger out-of-the-box.

All dependencies via libs.version.toml. Setup via environment variables.

## Local development (Linux Ubuntu)

Clone repo from git clone

```git clone git@github.com:radiopapus/kotlin-rest-template.git```

Install
[java 17](https://sdkman.io/usage),
[kotlin  1.8.x](https://kotlinlang.org/docs/command-line.html#sdkman),
[gradle 8.x](https://gradle.org/install/),
docker 20.x.

Fill next environment variables for .env ( see .env-example for details).

Run postgres container ```docker compose postgres```

Run migration ```gradle flywayMigrate```

Run `gradle bootRun` to run application locally.

"[RestTemplateApplicationKt] - Started RestTemplateApplicationKt" in logs means app started and ready for test.

Visit http://localhost:{PORT}/swagger-ui/index.html for api documentation.

## Docker

You can easily run application in docker.

Run `gradle bootBuildImage`

Edit .env and replace DB_HOST=localhost to DB_HOST=postgres.

Run `docker compose up`

### Todo

Organize plugins and setup extension for plugins separately.

Provide ssl config disabled by default and properties to enable.
