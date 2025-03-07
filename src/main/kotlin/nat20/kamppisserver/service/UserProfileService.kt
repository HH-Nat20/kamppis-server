package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.domain.toUserProfileDTO
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Service class for User Profile.
 */
@Service
class UserProfileService(
    private val repository: UserProfileRepository,
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository
) {

    // private final val userProfileRepository: UserProfileRepository = TODO("initialize me")

    /**
     * Returns all User Profiles as DTOs.
     * For testing purposes only.
     *
     * @return all User Profiles as DTOs.
     */
    fun findAll(): List<UserProfileDTO> {
        val userProfileList = repository.findAll()
        return userProfileList.map {toUserProfileDTO(it)}
    }

    /**
     * Returns User Profile by id.
     *
     * @param id the id of the profile to be returned.
     * @return the profile with given id.
     */
    fun findById(id: Long): UserProfileDTO {
        val userProfile = repository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("User profile with id $id not found")

        return toUserProfileDTO(userProfile)
    }

    /**
     * Creates new User Profile.
     *
     * @param userProfile the profile to be created.
     * @return the created profile.
     */
    fun add(userProfile: UserProfile): UserProfileDTO {
        userRepository.findByIdOrNull(userProfile.user.id)
            ?: throw EntityNotFoundException("User ${userProfile.user.id} not found")

        val addedUserProfile = userProfileRepository.save(userProfile)
        return toUserProfileDTO(addedUserProfile)
    }

    /**
     * Updates given User Profile.
     *
     * @param userProfile the profile to be updated.
     * @param id the id of the profile to be updated.
     * @return the updated profile.
     */
    @Transactional
    fun update(userProfile: UserProfileDTO, id: Long): UserProfileDTO {
        val existingProfile = repository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("User profile with id $id not found")

        existingProfile.firstName = userProfile.firstName
        existingProfile.lastName = userProfile.lastName
        // TODO: age updating? Currently not possible with the DTO structure
        existingProfile.gender = userProfile.gender
        // TODO: updating user photos
        // Kotlin shorthand for only updating if the new value is not null
        userProfile.bio?.let { existingProfile.bio = it }
        userProfile.preferredLocations?.let { existingProfile.preferredLocations = it }
        existingProfile.maxRent = userProfile.maxRent
        existingProfile.cleanliness = userProfile.cleanliness
        userProfile.lifestyle?.let { existingProfile.lifestyle = it }

        existingProfile.updatedAt = LocalDateTime.now()

        val updatedProfile = repository.save(existingProfile)

        return toUserProfileDTO(updatedProfile)
    }

    /**
     * Soft deletes given User Profile.
     *
     * @param id the id of the profile to be deleted.
     */
    fun delete(id: Long) {
        val deletedProfile = repository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("User profile with id $id not found")

        deletedProfile.deletedAt = LocalDateTime.now()
        repository.save(deletedProfile)
    }

}