package com.example.template.healthcheck

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckEndpoint {

    @GetMapping("/health")
    fun health(): String = "OK"
}