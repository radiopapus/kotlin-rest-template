package com.example.template.rest.example

import com.example.template.RestTemplateApplication
import com.example.template.TestConfig
import com.example.template.database.HibernateConfig
import com.example.template.database.TestDataService
import com.example.template.example.Example
import com.example.template.example.ExampleRepository
import com.example.template.example.ExampleRequest
import com.example.template.example.ExampleResponse
import com.example.template.example.UpdateExampleRequest
import io.restassured.RestAssured
import io.restassured.RestAssured.get
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.http.ContentType
import java.util.UUID
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
    classes = [TestConfig::class, HibernateConfig::class, TestDataService::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(classes = [RestTemplateApplication::class])
@Testcontainers
class ExampleRestTest(
    @Autowired val exampleRepository: ExampleRepository
) {

    @Autowired
    lateinit var tdm: TestDataService

    @LocalServerPort
    val port: Int = 9090

    val baseEndpoint: String = "/example"

    @BeforeEach
    fun setup() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        tdm.truncateTables()
    }

    @Test
    fun `test fetching example with success`() {
        get(baseEndpoint)
            .then()
            .statusCode(HttpStatus.OK.value())
            .assertThat().body("", empty<ExampleResponse>())
    }

    @Test
    fun `test add example`() {
        val expectedId = UUID.randomUUID()
        val expectedText = "expectedText"

        val example = ExampleRequest(expectedId, expectedText)

        val requestSpec = with(RequestSpecBuilder()) {
            setContentType(ContentType.JSON)
            setBody(example)
            build()
        }

        val responseSpec = with(ResponseSpecBuilder()) {
            expectStatusCode(HttpStatus.CREATED.value())
            expectBody("text", equalTo(expectedText))
            expectBody("id", equalTo("$expectedId"))
            build()
        }

        given()
            .spec(requestSpec)
            .post(baseEndpoint)
            .then()
            .spec(responseSpec)
    }

    @Test
    fun `test update example`() {
        val expectedId = UUID.randomUUID()
        val expectedText = "expectedText"
        val expectedUpdatedText = "updatedText"

        val example = Example(expectedId, expectedText)
        exampleRepository.save(example)

        val exampleForUpdate = UpdateExampleRequest(expectedUpdatedText)

        val requestSpec = with(RequestSpecBuilder()) {
            setContentType(ContentType.JSON)
            setBody(exampleForUpdate)
            build()
        }

        val responseSpec = with(ResponseSpecBuilder()) {
            expectStatusCode(HttpStatus.OK.value())
            expectBody("text", equalTo(expectedUpdatedText))
            expectBody("id", equalTo("$expectedId"))
            build()
        }

        given()
            .spec(requestSpec)
            .put("$baseEndpoint/$expectedId")
            .then()
            .spec(responseSpec)
    }
}
