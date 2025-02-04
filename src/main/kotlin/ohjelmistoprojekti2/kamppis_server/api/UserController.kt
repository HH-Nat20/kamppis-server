package ohjelmistoprojekti2.kamppis_server.api

import ohjelmistoprojekti2.kamppis_server.domain.User
import ohjelmistoprojekti2.kamppis_server.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository){

    @GetMapping("/")
    fun findAll(): MutableIterable<User> = repository.findAll()

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long) = repository.findByIdOrNull(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}