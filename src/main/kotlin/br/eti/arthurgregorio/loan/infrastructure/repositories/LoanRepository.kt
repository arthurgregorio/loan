package br.eti.arthurgregorio.loan.infrastructure.repositories

import br.eti.arthurgregorio.loan.domain.entities.Loan
import org.springframework.stereotype.Repository

@Repository
interface LoanRepository : DefaultRepository<Loan> {

    fun findByStatus(status: Loan.Status): List<Loan>
}