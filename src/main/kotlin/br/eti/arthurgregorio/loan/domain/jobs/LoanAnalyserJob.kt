package br.eti.arthurgregorio.loan.domain.jobs

import br.eti.arthurgregorio.loan.domain.entities.Loan
import br.eti.arthurgregorio.loan.domain.services.LoanService
import br.eti.arthurgregorio.loan.domain.validation.LoanValidator
import br.eti.arthurgregorio.loan.infrastructure.repositories.LoanRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class LoanAnalyserJob(
    private val loanService: LoanService,
    private val loanValidator: LoanValidator,
    private val loanRepository: LoanRepository
) {

    @Scheduled(fixedDelay = 120000)
    fun analyse() {
        loanRepository.findByStatus(Loan.Status.IN_ANALYSIS)
            .forEach { loan -> checkForApproval(loan) }
    }

    private fun checkForApproval(loan: Loan) {
        if (loanValidator.isValid(loan)) {
            loanService.approve(loan)
        } else {
            loanService.deny(loan)
        }
    }
}