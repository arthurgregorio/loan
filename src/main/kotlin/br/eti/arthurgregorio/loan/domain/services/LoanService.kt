package br.eti.arthurgregorio.loan.domain.services

import br.eti.arthurgregorio.loan.domain.entities.Loan
import br.eti.arthurgregorio.loan.domain.entities.Person
import br.eti.arthurgregorio.loan.domain.exceptions.PersonIsUnderAge
import br.eti.arthurgregorio.loan.infrastructure.metrics.MetricsCounter
import br.eti.arthurgregorio.loan.infrastructure.repositories.LoanRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
@Transactional(readOnly = true)
class LoanService(
    private val metricsCounter: MetricsCounter,
    private val loanRepository: LoanRepository
) {

    @Transactional
    fun generateProposal(person: Person, requestedValue: BigDecimal, monthlyPayment: BigDecimal): Loan {

        val interestRate = defineInterestRate(person)
        val interestValue = requestedValue.multiply(interestRate).divide(BigDecimal(100))
        val monthsToPay = calculateMonthsToPay(requestedValue.add(interestValue), monthlyPayment)

        val loan = Loan(interestRate, monthsToPay, requestedValue, interestValue, person)

        val saved = loanRepository.save(loan)

        metricsCounter.increase("loan_in_analysis")

        return saved
    }

    @Transactional
    fun approve(loan: Loan) {
        loan.status = Loan.Status.APPROVED
        loanRepository.save(loan)
        metricsCounter.increase("loan_approved")
    }

    @Transactional
    fun deny(loan: Loan) {
        loan.status = Loan.Status.DENIED
        loanRepository.save(loan)
        metricsCounter.increase("loan_denied")
    }

    private fun calculateMonthsToPay(requestedValue: BigDecimal, monthlyPayment: BigDecimal): Double {
        return requestedValue.divide(monthlyPayment, RoundingMode.UP).toDouble()
    }

    private fun defineInterestRate(person: Person): BigDecimal {

        val age = ChronoUnit.YEARS.between(person.birthDate, LocalDate.now())

        return when {
            age >= 60 -> {
                BigDecimal(10)
            }
            age in 30..59 -> {
                BigDecimal(20)
            }
            age <= 29 -> {
                BigDecimal(30)
            }
            else -> throw PersonIsUnderAge()
        }
    }
}