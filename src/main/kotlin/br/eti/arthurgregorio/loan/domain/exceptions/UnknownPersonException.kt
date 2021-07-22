package br.eti.arthurgregorio.loan.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UnknownPersonException(personId: UUID) : RuntimeException("Can't find any person with id [$personId]")