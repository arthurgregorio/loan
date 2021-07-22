package br.eti.arthurgregorio.loan.infrastructure.repositories

import br.eti.arthurgregorio.loan.domain.entities.Person
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : DefaultRepository<Person>