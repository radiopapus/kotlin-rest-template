package com.example.template

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait

@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class])
@TestConfiguration(proxyBeanMethods = false)
class TestConfig {
    private val dbName = "example_test"

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun postgres(): JdbcDatabaseContainer<*> = PostgreSQLContainer("postgres:15.3").apply {
        withDatabaseName(dbName)
        withInitScript("database/init.sql")
        waitingFor(
            Wait.forListeningPort()
        )
    }

    @Bean
    fun dataSource(jdbcDatabaseContainer: JdbcDatabaseContainer<*>): DataSource {
        val hikariConfig = HikariConfig()
        with(hikariConfig) {
            jdbcUrl = jdbcDatabaseContainer.jdbcUrl
            username = jdbcDatabaseContainer.username
            password = jdbcDatabaseContainer.password
        }

        return HikariDataSource(hikariConfig)
    }
}
