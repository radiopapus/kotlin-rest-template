package com.example.template.example

import com.example.template.NotFoundException
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ExampleService(private val exampleRepository: ExampleRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun fetchAll(): Set<Example> = exampleRepository.findAll().toSet()

    fun fetchById(id: UUID): Example = exampleRepository.findById(id).orElseThrow {
        NotFoundException("Example entity with id {id} not found")
    }

    fun updateExample(id: UUID, exampleRequest: UpdateExampleRequest): Example {
        val example = fetchById(id)

        example.text = exampleRequest.text

        return exampleRepository.save(example)
    }

    fun saveExample(exampleRequest: ExampleRequest): Example {
        logger.info("example: $exampleRequest")

        val example = mapRequestToModel(exampleRequest)
        exampleRepository.save(example)
        return example
    }

    fun deleteExample(id: UUID) {
        logger.info("delete example with id: $id")

        exampleRepository.deleteById(id)
    }
}
