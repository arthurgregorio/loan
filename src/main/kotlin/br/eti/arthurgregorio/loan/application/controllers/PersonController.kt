package br.eti.arthurgregorio.loan.application.controllers

import br.eti.arthurgregorio.loan.application.payloads.PersonDetail
import br.eti.arthurgregorio.loan.application.payloads.PersonForm
import br.eti.arthurgregorio.loan.domain.entities.Person
import br.eti.arthurgregorio.loan.domain.exceptions.ResourceNotFoundException
import br.eti.arthurgregorio.loan.domain.services.PersonService
import br.eti.arthurgregorio.loan.infrastructure.repositories.PersonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping("/api/persons")
class PersonController(
    private val personService: PersonService,
    private val personRepository: PersonRepository
) {

    @GetMapping
    fun getAll(pageable: Pageable): ResponseEntity<Page<PersonDetail>> {
        return personRepository.findAll(pageable)
            .map { PersonDetail.of(it) }
            .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<PersonDetail> {
        return personRepository.findByExternalId(id)
            ?.let { PersonDetail.of(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: throw ResourceNotFoundException(id)
    }

    @PostMapping
    fun create(@RequestBody @Valid personForm: PersonForm): ResponseEntity<Any> {

        val created = personService.save(Person.of(personForm))

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.externalId)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}