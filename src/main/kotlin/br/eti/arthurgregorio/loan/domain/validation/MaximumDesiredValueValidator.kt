package br.eti.arthurgregorio.loan.domain.validation

import br.eti.arthurgregorio.loan.domain.entities.Loan
import br.eti.arthurgregorio.loan.infrastructure.metrics.LoanValidationMetrics
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class MaximumDesiredValueValidator : LoanValidation {

    override fun validate(loan: Loan): Boolean {
        return loan.requestedValue <= BigDecimal(500000)
    }

    override fun onErrorMetric(): String {
        return LoanValidationMetrics.LOAN_VALUE_TOO_HIGH
    }
}