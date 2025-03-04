package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.JsonNode
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
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
 * Rest controller for User.
 */
@RestController
@RequestMapping("/api/users")
class UserController(private val repository: UserRepository, private val userService: UserService) {

    @GetMapping("", "/")
    fun findAll(): MutableIterable<User> = repository.findAll()

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long) = repository.findByIdOrNull(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")

    /**
     * Creates new user.
     *
     * @param user the user to be created.
     * @return ResponseEntity with status code 201 CREATED.
     */
    @PostMapping
    fun addUserProfile(@RequestBody user: User): ResponseEntity<User>
            = ResponseEntity.status(HttpStatus.CREATED).body(userService.add(user))

    /**
     * Updates user.
     *
     * @param user the user to be updated.
     * @param id the id of the user to be updated.
     * @return ResponseEntity with status code 200 OK.
     */
    @PutMapping("/{id}")
    fun updateUserProfile(@RequestBody user: User, @PathVariable id: Long): ResponseEntity<User>
            = ResponseEntity.ok(userService.update(user, id))

}