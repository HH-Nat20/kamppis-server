package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserProfileRepository
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val roomProfileRepository: RoomProfileRepository,
    private val userProfileRepository: UserProfileRepository,
    private val roomProfileService: RoomProfileService,
    private val userProfileService: UserProfileService,
) {

    /**
     * Returns all active Profiles as DTOs.
     */
    fun findAll(): List<ProfileDTO> {
        val roomProfiles = roomProfileRepository.findAllActive().map { it.toDTO(includeUserSummary = true) }
        val userProfiles = userProfileRepository.findAllActive().map { it.toDTO(includeUserSummary = true) }

        val profiles = roomProfiles + userProfiles

        return profiles
    }

    /**
     * Returns active Profile by id.
     *
     * @param id the id of the profile to be returned.
     * @return the profile with given id.
     */
    fun findById(id: Long): ProfileDTO {
        val roomProfile = roomProfileRepository.findByIdActive(id)
        val userProfile = userProfileRepository.findByIdActive(id)

        when {
            roomProfile != null -> {
                println("Found RoomProfile ID: ${roomProfile.id}, Photos: ${roomProfile.photos}")
                return roomProfile.toDTO(includeUserSummary = true)
            }

            userProfile != null -> {
                println("Found UserProfile ID: ${userProfile.id}, Photos: ${userProfile.photos}")
                return userProfile.toDTO(includeUserSummary = true)
            }

            else -> throw EntityNotFoundException("Profile with id $id not found")
        }
    }

}