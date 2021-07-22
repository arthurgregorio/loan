package br.eti.arthurgregorio.loan.infrastructure.metrics

object LoanValidationMetrics {

    const val PERSON_SCORE_IS_TOO_LOW: String = "loan.validation.person_score_is_too_low"
    const val LOAN_VALUE_TOO_LOW: String = "loan.validation.loan_value_too_low"
    const val LOAN_VALUE_TOO_HIGH: String = "loan.validation.loan_value_too_high"
}