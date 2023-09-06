package com.example.template

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestTemplateApplication

fun main(args: Array<String>) {
    runApplication<RestTemplateApplication>(*args)
}

