# Kotlin Rest Template

This is a kotlin web application template based on spring boot 3 and (twelve factors)[https://12factor.net/].

Kotlin, Gradle, Spring Boot 3, Hibernate, Postgres, Flyway, TestContainers, RestAssured, ExceptionHandler
Swagger out-of-the-box.

All dependencies via libs.version.toml.

# Local development

Instruction works for Ubuntu users.

Clone repo from git clone

```git clone git@github.com:radiopapus/kotlin-rest-template.git```

Install
[java](https://sdkman.io/usage),
[kotlin](https://kotlinlang.org/docs/command-line.html#sdkman),
[gradle](https://gradle.org/install/), docker stuff.

Fill next environment variables for .env ( see .env-example for details).

Run `gradle bootRun`

"[RestTemplateApplicationKt] - Started RestTemplateApplicationKt" in logs means app started and ready for test.

Visit http://localhost:{PORT}/swagger-ui/index.html for api documentation.

# Todo

Organize plugins and setup extension for plugins separately.
Provide ssl config disabled by default and properties to enable.
