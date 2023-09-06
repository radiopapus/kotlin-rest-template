package com.example.template.smoke

import com.example.template.TestConfig
import com.example.template.database.HibernateConfig
import com.example.template.database.TestDataService
import com.example.template.example.Example
import com.example.template.example.ExampleRepository
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(classes = [TestConfig::class, HibernateConfig::class, TestDataService::class])
@Testcontainers
class SmokeTestContainerTest(
    @Autowired val exampleRepository: ExampleRepository
) {

    @Test
    fun `test save and find example model by id`() {
        val (id, text) = Pair(UUID.randomUUID(), "text")

        with(exampleRepository) {
            save(Example(id, text))

            findByIdOrNull(id)?.let {
                assertEquals(it.id, id)
                assertEquals(it.text, text)
            }
        }
    }
}
