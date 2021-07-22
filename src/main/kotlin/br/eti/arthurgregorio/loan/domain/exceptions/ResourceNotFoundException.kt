package br.eti.arthurgregorio.loan.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

@ResponseStatus(HttpStatus.NO_CONTENT)
class ResourceNotFoundException(resourceId: UUID) : RuntimeException("Can't find any resource with id [$resourceId]")