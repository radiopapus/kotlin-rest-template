package com.example.template

import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.util.UUID

class NotFoundException(override val message: String, cause: Throwable? = null) : RuntimeException(message, cause)

class BadRequestException(override val message: String, cause: Throwable? = null) : RuntimeException(message, cause)

interface ServerError {
    val status: Int
    val code: String
    val id: UUID
    val message: String?
}

class NotFound(
    override val message: String,
    override val status: Int = HTTP_NOT_FOUND,
    override val code: String = "NF",
    override val id: UUID = UUID.randomUUID()
) : ServerError

class InternalServerError(
    override val message: String?,
    override val status: Int = HTTP_INTERNAL_ERROR,
    override val code: String = "IE",
    override val id: UUID = UUID.randomUUID()
) : ServerError

class BadRequest(
    override val message: String,
    override val status: Int = HTTP_BAD_REQUEST,
    override val code: String = "BR",
    override val id: UUID = UUID.randomUUID()
) : ServerError
