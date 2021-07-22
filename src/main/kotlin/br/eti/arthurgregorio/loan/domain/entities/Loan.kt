package br.eti.arthurgregorio.loan.domain.entities

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "loans")
class Loan(
    @field:Column(name = "interest_rate", nullable = false)
    val interestRate: BigDecimal,
    @field:Column(name = "months_to_pay", nullable = false)
    val monthsToPay: Double,
    @field:Column(name = "requested_value", nullable = false)
    val requestedValue: BigDecimal,
    @field:Column(name = "interest_value", nullable = false)
    val interestValue: BigDecimal,

    @field:ManyToOne(optional = false)
    @field:JoinColumn(name = "id_person", nullable = false)
    var person: Person,

    @field:Enumerated(EnumType.STRING)
    @field:Column(name = "status", nullable = false)
    var status: Status = Status.IN_ANALYSIS,
    @field:Column(name = "reason_of_denial", columnDefinition = "TEXT")
    var reasonOfDenial: String? = null,
) : PersistentEntity<Long>() {

    fun totalValue(): BigDecimal {
        return requestedValue.add(interestValue)
    }

    enum class Status {
        IN_ANALYSIS, APPROVED, DENIED
    }
}

