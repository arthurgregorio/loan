package br.eti.arthurgregorio.loan.application.payloads

import br.eti.arthurgregorio.loan.domain.entities.Loan
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID
import javax.persistence.criteria.Predicate
import javax.validation.constraints.NotNull

data class LoanFilter(
    val filter: String?,
    var status: Loan.Status?
) {
    fun toSpecification(): Specification<Loan> {
        return Specification<Loan> { root, _, builder ->

            val predicates = mutableListOf<Predicate>()

            if (!filter.isNullOrBlank()) {
                predicates.add(
                    builder.or(
                        builder.like(builder.lower(root.get("person.name")), filter.lowercase()),
                        builder.like(builder.lower(root.get("person.email")), filter.lowercase())
                    )
                )
            }

            if (status != null) {
                predicates.add(builder.equal(root.get<String>("status"), status!!.name))
            }

            builder.and(*predicates.toTypedArray())
        }
    }
}

data class LoanDetail(
    val id: UUID,
    val interestRate: BigDecimal,
    val monthsToPay: Double,
    val totalValue: BigDecimal,
    val status: Loan.Status,
    val reasonOfDenial: String?,
    val person: PersonDetail
) {
    companion object {
        fun of(loan: Loan): LoanDetail {
            return LoanDetail(
                loan.externalId!!,
                loan.interestRate,
                loan.monthsToPay,
                loan.totalValue(),
                loan.status,
                loan.reasonOfDenial,
                PersonDetail.of(loan.person)
            )
        }
    }
}

data class LoanRequest(
    @field:NotNull
    val person: UUID,
    @field:NotNull
    val value: BigDecimal,
    @field:NotNull
    val monthlyPayment: BigDecimal
)