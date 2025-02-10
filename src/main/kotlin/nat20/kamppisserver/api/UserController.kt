package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.JsonNode
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.QueryService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    @GetMapping("/")
    fun findAll(): MutableIterable<User> = repository.findAll()

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long) = repository.findByIdOrNull(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}