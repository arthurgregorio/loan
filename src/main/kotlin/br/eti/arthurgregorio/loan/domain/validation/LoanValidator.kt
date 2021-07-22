package br.eti.arthurgregorio.loan.domain.validation

import br.eti.arthurgregorio.loan.domain.entities.Loan
import br.eti.arthurgregorio.loan.infrastructure.metrics.MetricsCounter
import org.springframework.stereotype.Component

@Component
class LoanValidator(
    private val loanValidations: List<LoanValidation>,
    private val metricsCounter: MetricsCounter
) {

    fun isValid(loan: Loan): Boolean {
        for (validation in loanValidations) {
            if (!validation.validate(loan)) {
                loan.reasonOfDenial = validation.onErrorMetric()
                metricsCounter.increase(validation.onErrorMetric())
                return false
            }
        }
        return true
    }
}