package com.example.template.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.TimeZone
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.web.client.RestTemplate

@Configuration(proxyBeanMethods = false)
class AppConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    @Bean
    @Primary
    fun objectMapperBuilder() = with(Jackson2ObjectMapperBuilder()) {
        // Allow reading enum values as a default using an enums declared @JsonEnumDefaultValue (if specified)
        // Depends on JSR310 JavaTime module, included by Spring by default
        featuresToEnable(
            DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE
        )
        featuresToDisable(
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
        )

        // Quoted "Z" to indicate UTC, no timezone offset
        val dateFormat = with(SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")) {
            timeZone = TimeZone.getTimeZone("UTC")
            this
        }

        timeZone(dateFormat.timeZone)
        dateFormat(dateFormat)

        val kotlinModule = KotlinModule.Builder().build()
        modulesToInstall(kotlinModule)
    }

    @Bean
    @ConditionalOnMissingBean
    fun retryTemplate(): RetryTemplate = with(RetryTemplate()) {
        setRetryPolicy(
            SimpleRetryPolicy(
                50,
                mutableMapOf(
                    ConnectException::class.java to true,
                    UnknownHostException::class.java to true,
                    SocketTimeoutException::class.java to true
                ),
                true
            )
        )
        setBackOffPolicy(ExponentialRandomBackOffPolicy())
        this
    }
}
