package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.domain.RoomProfileDTO
import nat20.kamppisserver.domain.UserProfileRequest
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.UserProfileService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * REST controller for User Profile.
 */
@RestController
@RequestMapping("/api/user-profiles")
@Validated
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
    fun addUserProfile(@Valid @RequestBody request: UserProfileRequest): ResponseEntity<UserProfileDTO>
        = ResponseEntity.status(HttpStatus.CREATED).body(service.add(request))

    /**
     * Updates user profile.
     *
     * @param userProfile the profile to be updated.
     * @param id the id of the profile to be updated.
     * @return ResponseEntity with status code 200 OK.
     */
    @PutMapping("/{id}")
    fun updateUserProfile(@Valid @RequestBody request: UserProfileRequest, @PathVariable id: Long): ResponseEntity<UserProfileDTO>
        = ResponseEntity.ok(service.update(request, id))

    /**
     * Finds all the user profiles that match the user's search criteria.
     *
     * @param id the id of the user who initiated the query.
     * @return ResponseEntity with list of user profiles and status code 200 OK if user profiles have been found.
     * @return ResponseEntity with status code 204 NO_CONTENT if no user profiles have been found.
     */
    @GetMapping("/{userId}/query")
    fun findUserProfilesThatMeetCriteria(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam size: Int,
        @PathVariable userId: Long): ResponseEntity<Page<UserProfileDTO>> {
        val userProfileList: Page<UserProfileDTO> = queryService.findUserProfilesThatMeetCriteria(PageRequest.of(page, size), userId)

        if (userProfileList.isEmpty) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }

        return ResponseEntity(userProfileList, HttpStatus.OK)
    }

    /**
     * Finds all the user profiles that have swiped user's room profile(s)
     *
     * @param id the id of the user who initiated the query.
     * @return ResponseEntity with map (key: roomProfileId, value: list of userProfiles) and status code 200 OK if user profiles have swiped the room profile.
     * @return ResponseEntity with map (key: roomProfileId, value: empty list of userProfiles) and status code 200 OK if no user profiles have swiped the room profile.
     * @return ResponseEntity with status code 204 NO_CONTENT if the user has no room profiles.
     */
    @GetMapping("/{userId}/roomswipesquery")
    fun findUserProfilesWhoHaveSwipedUsersRoom(@PathVariable userId: Long): ResponseEntity<Map<Long, List<UserProfileDTO>>> {
        val userProfileMap: Map<Long, List<UserProfileDTO>> = queryService.findUserProfilesThatHaveSwipedUsersRoom(userId)

        if (userProfileMap.isEmpty()) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }

        return ResponseEntity(userProfileMap, HttpStatus.OK)
    }
}