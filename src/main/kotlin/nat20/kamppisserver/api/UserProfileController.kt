package nat20.kamppisserver.api

import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.UserProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller for User Profile.
 */
@RestController
@RequestMapping("/api/user-profiles")
class UserProfileController(private val service: UserProfileService,
                            private val queryService: QueryService) {

    /**
     * Returns all user profiles as DTOs.
     * For testing purposes only.
     *
     * @return ResponseEntity with status code 200 OK.
     */
    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<UserProfileDTO>> {
        return ResponseEntity.ok(service.findAll())
    }

    /**
     * Returns user profile by id.
     */
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<UserProfileDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    /**
     * Creates new user profile.
     *
     * @param userProfile the profile to be created.
     * @return ResponseEntity with status code 201 CREATED.
     */
    @PostMapping
    fun addUserProfile(@RequestBody userProfile: UserProfile): ResponseEntity<UserProfileDTO>
        = ResponseEntity.status(HttpStatus.CREATED).body(service.add(userProfile))

    /**
     * Updates user profile.
     *
     * @param userProfile the profile to be updated.
     * @param id the id of the profile to be updated.
     * @return ResponseEntity with status code 200 OK.
     */
    @PutMapping("/{id}")
    fun updateUserProfile(@RequestBody userProfile: UserProfileDTO, @PathVariable id: Long): ResponseEntity<UserProfileDTO>
        = ResponseEntity.ok(service.update(userProfile, id))

    /**
     * Finds all the user profiles that match the user's search criteria.
     *
     * @param id the id of the user who initiated the query.
     * @return ResponseEntity with list of user profiles and status code 200 OK if user profiles have been found.
     * @return ResponseEntity with status code 204 NO_CONTENT if no user profiles have been found.
     */
    @GetMapping("/{id}/query")
    fun findUserProfilesThatMeetCriteria(@PathVariable id: Long): ResponseEntity<MutableList<UserProfileDTO>> {
        val userProfileList: MutableList<UserProfileDTO> = queryService.findUserProfilesThatMeetCriteria(id)

        if (userProfileList.isEmpty()) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }

        return ResponseEntity(userProfileList, HttpStatus.OK)
    }
}