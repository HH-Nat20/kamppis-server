package ohjelmistoprojekti2.kamppis_server.api

import ohjelmistoprojekti2.kamppis_server.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.optionals.getOrElse

@RestController
@RequestMapping("/api/user")
class UserRestController(private val repository: UserRepository){

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long) = repository.findById(id).getOrElse {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist") }
}