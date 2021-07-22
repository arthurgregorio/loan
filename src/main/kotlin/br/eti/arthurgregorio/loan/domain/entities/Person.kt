package br.eti.arthurgregorio.loan.domain.entities

import br.eti.arthurgregorio.loan.application.payloads.PersonForm
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "persons")
class Person(
    @field:Column(name = "name", length = 150, nullable = false)
    var name: String,
    @field:Column(name = "email", length = 150, nullable = false)
    var email: String,
    @field:Column(name = "document", length = 45, nullable = false)
    var document: String,
    @field:Column(name = "birth_date", nullable = false)
    var birthDate: LocalDate
) : PersistentEntity<Long>() {

    companion object {
        fun of(personForm: PersonForm): Person {
            return Person(personForm.name, personForm.email, personForm.document, personForm.birthDate)
        }
    }
}