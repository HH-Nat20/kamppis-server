package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDateTime

/**
 * Service class for User Profile.
 */
@Service
class UserProfileService(private val repository: UserProfileRepository) {

    /**
     * Updates given User Profile.
     *
     * @param userProfile the profile to be updated.
     * @param id the id of the profile to be updated.
     * @return the updated profile.
     */
    fun update(userProfile: UserProfile, id: Long): UserProfile {
        val updatedProfile = repository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("User profile with id $id not found")

        updatedProfile.user = userProfile.user
        updatedProfile.minAgePreference = userProfile.minAgePreference
        updatedProfile.maxAgePreference = userProfile.maxAgePreference
        updatedProfile.preferredGender = userProfile.preferredGender
        updatedProfile.locations = userProfile.locations
        updatedProfile.bio = userProfile.bio
        updatedProfile.updatedAt = LocalDateTime.now()

        return repository.save(updatedProfile)
    }

}