package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.JsonNode
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

/**
 * Rest controller for User. Provides simple GET methods.
 */
@RestController
@RequestMapping("/api/users")
class UserController(private val repository: UserRepository) {

    @GetMapping("", "/")
    fun findAll(): MutableIterable<User> = repository.findAll()

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long) = repository.findByIdOrNull(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")

    /** Still experimenting, might never be used
    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    fun addUser(@Payload user: User): User {
        service.saveUser(user)
        return user
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    fun disconnect(@Payload user: User): User {
        service.disconnect(user)
        return user
    }

    @GetMapping("/users")
    fun findConnectedUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(service.findConnectedUsers())
    }
    */

}