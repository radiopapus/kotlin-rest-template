package com.example.template.example

import java.util.UUID

data class ExampleRequest(val id: UUID?, val text: String)
data class UpdateExampleRequest(val text: String)

data class ExampleResponse(val id: UUID?, val text: String)

internal fun mapRequestToModel(exampleRequest: ExampleRequest): Example = Example(
    exampleRequest.id ?: UUID.randomUUID(),
    exampleRequest.text
)

internal fun mapModelToResponse(exampleModel: Example): ExampleResponse = ExampleResponse(
    exampleModel.id,
    exampleModel.text
)
