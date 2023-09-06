package com.example.template.example

import com.example.template.BaseAuditModel
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table
class Example(
    id: UUID,

    @Column(nullable = false)
    var text: String
) : BaseAuditModel(id)
