package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.domain.ProfilePhoto
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Service class for User Profile.
 */
@Service
class UserProfileService(
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository
) {

    /**
     * Returns all active User Profiles as DTOs.
     * For testing purposes only.
     *
     * @return all User Profiles as DTOs.
     */
    fun findAll(): List<UserProfileDTO> {
        val userProfileList = userProfileRepository.findAllActive()
        return userProfileList.map { it.toDTO(includeUserSummary = true) }
    }

    /**
     * Returns active User Profile by id.
     *
     * @param id the id of the profile to be returned.
     * @return the profile with given id.
     */
    fun findById(id: Long): UserProfileDTO {
        val userProfile = userProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("User profile with id $id not found")

        return userProfile.toDTO(includeUserSummary = true)
    }

    /**
     * Creates new User Profile.
     *
     * @param userProfile the profile to be created.
     * @return the created profile.
     */
    fun add(userProfile: UserProfile): UserProfileDTO {
        userProfile.user.id?.let { userRepository.findByIdAndStatus(it, UserStatus.ACTIVE) }
            ?: throw EntityNotFoundException("User ${userProfile.user.id} not found")

        val addedUserProfile = userProfileRepository.save(userProfile)
        return addedUserProfile.toDTO(includeUserSummary = true)
    }

    /**
     * Updates given active User Profile.
     *
     * @param userProfile the profile to be updated.
     * @param id the id of the profile to be updated.
     * @return the updated profile.
     */
    @Transactional
    fun update(@Valid userProfile: UserProfileDTO, id: Long): UserProfileDTO {
        val existingProfile = userProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("User profile with id $id not found")

        // Apply updates only if new values are not null
        userProfile.bio.let { existingProfile.bio = it }
        userProfile.cleanliness?.let { existingProfile.cleanliness = it }
        userProfile.lifestyle?.let { existingProfile.lifestyle = it }

        // Convert ProfilePhotoDTOs to ProfilePhoto entities
        userProfile.photos.let {
            existingProfile.photos = it.map { dto ->
                ProfilePhoto(
                    profile = existingProfile,
                    url = dto.url,
                    isProfilePhoto = dto.isProfilePhoto,
                    id = dto.id
                )
            }.toMutableList()
        }

        existingProfile.updatedAt = LocalDateTime.now()

        val updatedProfile = userProfileRepository.save(existingProfile)

        return updatedProfile.toDTO(includeUserSummary = true)
    }

}