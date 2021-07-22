package br.eti.arthurgregorio.loan.application.payloads

import br.eti.arthurgregorio.loan.domain.entities.Person
import java.time.LocalDate
import java.util.UUID
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

data class PersonDetail(
    var id: UUID,
    var name: String,
    var email: String,
    var document: String,
    var birthDate: LocalDate
) {
    companion object {
        fun of(person: Person): PersonDetail {
            return PersonDetail(person.externalId!!, person.name, person.email, person.document, person.birthDate)
        }
    }
}

data class PersonForm(
    @field:NotBlank
    val name: String,
    @field:Email
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val document: String,
    @field:Past
    @field:NotNull
    val birthDate: LocalDate
)