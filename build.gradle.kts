import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.example"
java.sourceCompatibility = JavaVersion.VERSION_17

plugins {
	alias(libs.plugins.kotlinJvm)
	alias(libs.plugins.kotlinSpring)
	alias(libs.plugins.kotlinJpa)
	alias(libs.plugins.dependencyManagement)
	alias(libs.plugins.springBoot)
	alias(libs.plugins.flyway)
	alias(libs.plugins.dotenv)
}

repositories {
	mavenCentral()
}

flyway {
	url = "jdbc:postgresql://${env.DB_HOST.value}:${env.DB_PORT.value}/${env.DB_NAME.value}"
	user = env.DB_USER.value
	password = env.DB_PASSWORD.value
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

dependencies {
	implementation(libs.bundles.starters)
	implementation(libs.bundles.kotlinBase)
	implementation(libs.bundles.db)

	// documentation
	implementation(libs.swagger)

	// tests
	testImplementation(libs.bundles.startersTest)
	testImplementation(libs.bundles.restAssuredAll)
	testImplementation(libs.bundles.testContainersJUnitPostgres)
}

tasks {
	withType<Test> {
		useJUnitPlatform()
		jvmArgs = listOf("-Duser.timezone=UTC")
		with(testLogging) {
			showStandardStreams = true
			exceptionFormat = TestExceptionFormat.FULL
		}
	}
	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}
}
