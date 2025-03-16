package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.JsonNode
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.domain.enums.UserStatus
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
class UserController(private val repository: UserRepository,
                     private val userService: UserService) {

    @GetMapping("", "/")
    fun findAll(): MutableIterable<User> = repository.findAllByStatus(UserStatus.ACTIVE)

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long) = repository.findByIdAndStatus(id, UserStatus.ACTIVE)
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

    /**
     * Soft deletes user and subsequent UserProfile.
     *
     * @param id the id of the user to be deleted.
     * @return ResponseEntity with status code 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    /**
     * Restores deleted user.
     *
     * @param user the user to be restored.
     * @param id the id of the user to be restored.
     * @return ResponseEntity with status code 200 OK.
     */
    @PutMapping("/{id}/restore")
    fun restoreById(@RequestBody user: User,@PathVariable id: Long): ResponseEntity<User>
        = ResponseEntity.ok(userService.restore(user, id))

}