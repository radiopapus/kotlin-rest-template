package com.example.template.database

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.hibernate.boot.Metadata
import org.hibernate.boot.model.relational.Database
import org.hibernate.engine.spi.SessionFactoryImplementor
import org.hibernate.integrator.spi.Integrator
import org.hibernate.jpa.boot.spi.IntegratorProvider
import org.hibernate.service.spi.SessionFactoryServiceRegistry
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.boot.test.context.TestComponent

typealias TableName = String

class HibernateConfig : HibernatePropertiesCustomizer {
    override fun customize(hibernateProps: MutableMap<String, Any>) {
        hibernateProps["hibernate.integrator_provider"] =
            IntegratorProvider { listOf<Integrator>(MetadataExtractorIntegrator) }
    }
}

object MetadataExtractorIntegrator : Integrator {
    lateinit var metadata: Metadata
    lateinit var database: Database

    override fun integrate(metadata: Metadata, sf: SessionFactoryImplementor, sr: SessionFactoryServiceRegistry) {
        this.database = metadata.database
        this.metadata = metadata
    }

    override fun disintegrate(
        sessionFactory: SessionFactoryImplementor,
        serviceRegistry: SessionFactoryServiceRegistry
    ) {
        // nothing to do
    }
}

@TestComponent
class TestDataService {

    @PersistenceContext
    lateinit var em: EntityManager

    private val tableNames = mutableListOf<TableName>()

    @PostConstruct
    fun collectTableNames() {
        // collect table names
        with(MetadataExtractorIntegrator.metadata) {
            tableNames.addAll(collectionBindings.map {
                it.collectionTable.exportIdentifier
            })
            tableNames.addAll(entityBindings.map {
                it.table.exportIdentifier
            })
        }
    }

    @Transactional
    fun truncateTables() {
        with(em) {
            // disable foreign key checks
            createNativeQuery("SET session_replication_role = 'replica'").executeUpdate()
            tableNames.map { createNativeQuery("TRUNCATE TABLE $it").executeUpdate() }
            // enable foreign key checks
            createNativeQuery("SET session_replication_role = 'origin'").executeUpdate()
        }
    }
}