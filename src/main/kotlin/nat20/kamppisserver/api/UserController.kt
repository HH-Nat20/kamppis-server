package nat20.kamppisserver.api

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.QueryService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository, private val queryService: QueryService) {

    @GetMapping("/")
    fun findAll(): MutableIterable<User> = repository.findAll()

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long) = repository.findByIdOrNull(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")

    @GetMapping("/query")
    fun findAllUsersWithSQL(): MutableIterable<User> {
        return queryService.findAllUsersWithSQL()
    }
}