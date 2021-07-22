package br.eti.arthurgregorio.loan.infrastructure.repositories

import br.eti.arthurgregorio.loan.domain.entities.PersistentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.util.UUID

@NoRepositoryBean
interface DefaultRepository<T : PersistentEntity<Long>> : JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    fun findByExternalId(uuid: UUID): T?
}