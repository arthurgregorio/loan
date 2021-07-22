package br.eti.arthurgregorio.loan.application.controllers

import br.eti.arthurgregorio.loan.application.payloads.LoanDetail
import br.eti.arthurgregorio.loan.application.payloads.LoanFilter
import br.eti.arthurgregorio.loan.application.payloads.LoanRequest
import br.eti.arthurgregorio.loan.domain.exceptions.ResourceNotFoundException
import br.eti.arthurgregorio.loan.domain.exceptions.UnknownPersonException
import br.eti.arthurgregorio.loan.domain.services.LoanService
import br.eti.arthurgregorio.loan.infrastructure.repositories.LoanRepository
import br.eti.arthurgregorio.loan.infrastructure.repositories.PersonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/api/loans")
class LoanController(
    private val loanService: LoanService,
    private val loanRepository: LoanRepository,
    private val personRepository: PersonRepository
) {

    @GetMapping
    fun getAll(loanFilter: LoanFilter, pageable: Pageable): ResponseEntity<Page<LoanDetail>> {
        return loanRepository.findAll(loanFilter.toSpecification(), pageable)
            .map { LoanDetail.of(it) }
            .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<LoanDetail> {
        return loanRepository.findByExternalId(id)
            ?.let { LoanDetail.of(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: throw ResourceNotFoundException(id)
    }

    @PutMapping("/confirm/{loanId}")
    fun confirm(@PathVariable @NotNull loanId: UUID): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }

    @PostMapping
    fun create(@RequestBody @Valid loanRequest: LoanRequest): ResponseEntity<Any> {

        val person = personRepository.findByExternalId(loanRequest.person)
            ?: throw UnknownPersonException(loanRequest.person)

        val created = loanService.generateProposal(person, loanRequest.value, loanRequest.monthlyPayment)

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.externalId)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}