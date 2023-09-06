package com.example.template.example

import java.util.UUID
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExampleRepository : CrudRepository<Example, UUID>
