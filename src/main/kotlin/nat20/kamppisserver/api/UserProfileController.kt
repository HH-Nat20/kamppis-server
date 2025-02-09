package nat20.kamppisserver.api

import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.service.UserProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for User Profile.
 */
@RestController
@RequestMapping("/api/user-profile")
class UserProfileController(private val service: UserProfileService) {

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

}