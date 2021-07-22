package br.eti.arthurgregorio.loan.domain.services

import br.eti.arthurgregorio.loan.domain.entities.Person
import br.eti.arthurgregorio.loan.infrastructure.metrics.MetricsCounter
import br.eti.arthurgregorio.loan.infrastructure.repositories.PersonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PersonService(
    private val metricsCounter: MetricsCounter,
    private val personRepository: PersonRepository
) {

    @Transactional
    fun save(person: Person): Person {
        val saved = personRepository.save(person)
        metricsCounter.increase("person_created")
        return saved
    }
}