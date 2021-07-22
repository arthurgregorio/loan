package br.eti.arthurgregorio.loan.domain.validation

import br.eti.arthurgregorio.loan.domain.entities.Loan

interface LoanValidation {

    fun validate(loan: Loan): Boolean

    fun onErrorMetric(): String
}