package nat20.kamppisserver.api

import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.UserProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller for User Profile.
 */
@RestController
@RequestMapping("/api/user-profile")
class UserProfileController(private val service: UserProfileService, private val queryService: QueryService) {

    /**
     * Updates user profile.
     *
     * @param userProfile the profile to be updated.
     * @param id the id of the profile to be updated.
     * @return ResponseEntity with status code 200 OK.
     */
    @PutMapping("/{id}")
    fun updateUserProfile(@RequestBody userProfile: UserProfile, @PathVariable id: Long): ResponseEntity<UserProfile>
        = ResponseEntity.ok(service.update(userProfile, id))

    /**
     * Soft deletes user profile.
     *
     * @param id the id of the profile to be deleted.
     * @return ResponseEntity with status code 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}/query")
    fun findUserProfilesThatMeetCriteria(@PathVariable id: Long): MutableIterable<UserProfile> {
        return queryService.findUserProfilesThatMeetCriteria(id)
    }
}