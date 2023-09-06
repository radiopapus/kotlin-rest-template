package com.example.template

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version
import java.util.UUID

@MappedSuperclass
abstract class BaseModel(@Id val id: UUID) {
    @JsonIgnore
    @Version
    var version: Long = 0

    override fun toString(): String = "${javaClass.simpleName}(id=$id)"
}
