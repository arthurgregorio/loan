package br.eti.arthurgregorio.loan.domain.validation

import br.eti.arthurgregorio.loan.domain.entities.Loan
import br.eti.arthurgregorio.loan.infrastructure.metrics.LoanValidationMetrics
import org.springframework.stereotype.Component

@Component
class PersonScoreValidation : LoanValidation {

    override fun validate(loan: Loan): Boolean {

        /*
            imagine here a nice logic to consult if you are a good payer, odd numbers means you are not
        */

        val lastNumber = loan.person.document.last().toString().toInt()
        return (lastNumber % 2) == 0
    }

    override fun onErrorMetric(): String {
        return LoanValidationMetrics.PERSON_SCORE_IS_TOO_LOW
    }
}