package com.example.template.example

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/example")
class ExampleEndpoint(private val exampleService: ExampleService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Operation(summary = "Get an example list")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Return an examples list",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Example::class)
                    )
                ]
            )
        ]
    )
    @GetMapping
    fun getExampleList() = exampleService.fetchAll()

    @Operation(summary = "Get an example by specified identifier.")
    @GetMapping("/{id}")
    fun getExample(@PathVariable id: UUID) = mapModelToResponse(exampleService.fetchById(id))

    @Operation(summary = "Update an example by specified identifier with request data.")
    @PutMapping("/{id}")
    fun updateExample(@PathVariable id: UUID, @RequestBody exampleRequest: UpdateExampleRequest) = mapModelToResponse(
        exampleService.updateExample(id, exampleRequest)
    )

    @Operation(summary = "Create new example by specified request data.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveExample(@RequestBody exampleRequest: ExampleRequest) = mapModelToResponse(
        exampleService.saveExample(exampleRequest)
    )

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteExample(@PathVariable id: UUID) {
        logger.warn("trying to delete example with id $id")
        exampleService.deleteExample(id)
    }
}
