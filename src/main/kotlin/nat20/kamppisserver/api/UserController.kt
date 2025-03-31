package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.UserProfileService
import nat20.kamppisserver.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * Rest controller for User.
 */
@RestController
@RequestMapping("/api/users")
@Validated
class UserController(
    private val userService: UserService
    ) {

    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<UserDTO>>
            = ResponseEntity.ok(userService.findAll())

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long): ResponseEntity<UserDTO>
            = ResponseEntity.ok(userService.findById(id))



    /**
     * Get copy of all user data.
    */
    @GetMapping("/{id}/copy")
    fun getCopyOfUserData(@PathVariable id: Long): ResponseEntity<UserDataDTO>
            = ResponseEntity.ok(userService.getCopyOfUserData(id))

    /**
     * Creates new user.
     *
     * @param user the user to be created.
     * @return ResponseEntity with status code 201 CREATED.
     */
    @PostMapping
    fun addUser(@Valid @RequestBody request: UserRequest): ResponseEntity<UserDTO>
            = ResponseEntity.status(HttpStatus.CREATED).body(userService.add(request))

    /**
     * Updates user.
     *
     * @param user the user to be updated.
     * @param id the id of the user to be updated.
     * @return ResponseEntity with status code 200 OK.
     */
    @PutMapping("/{id}")
    fun updateUser(@Valid @RequestBody request: UserRequest, @PathVariable id: Long): ResponseEntity<UserDTO>
            = ResponseEntity.ok(userService.update(request, id))

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
    fun restoreById(@PathVariable id: Long): ResponseEntity<UserDTO>
        = ResponseEntity.ok(userService.restore(id))

    /**
     * Find user preferences.
     */
    @GetMapping("/{id}/preferences")
    fun getUserPreferences(@PathVariable id: Long): ResponseEntity<UserPreferenceDTO>
            = ResponseEntity.ok(userService.getPreferences(id))

    /**
     * Update user preferences.
     */
    @PutMapping("/{id}/preferences")
    fun updateUserPreferences(@Valid @RequestBody request: UserPreferenceRequest, @PathVariable id: Long): ResponseEntity<UserPreferenceDTO>
            = ResponseEntity.ok(userService.updatePreferences(request, id))

}