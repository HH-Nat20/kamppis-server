package nat20.kamppisserver.service

import exception.EntityNotFoundException
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserProfileRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val roomProfileRepository: RoomProfileRepository,
    private val userProfileRepository: UserProfileRepository,
    private val userProfileService: UserProfileService,
    private val roomProfileService: RoomProfileService
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

    fun updateProfile(profile: ProfileDTO, id: Long): ProfileDTO? {
        val userProfile = userProfileRepository.findByIdActive(id)
        val roomProfile = roomProfileRepository.findByIdActive(id)

        when {
            userProfile != null && profile is UserProfileDTO -> {
                val userProfileRequest = UserProfileRequest(
                    userId = profile.userId,
                    bio = profile.bio,
                    cleanliness = profile.cleanliness,
                    lifestyle = profile.lifestyle,
                    pets = profile.pets,
                    photos = profile.photos,
                    id = profile.id
                )
                return userProfileService.update(userProfileRequest, id)
            }

            roomProfile != null && profile is RoomProfileDTO -> {
                val roomProfileRequest = RoomProfileRequest(
                    userIds = profile.userIds,
                    flatId = profile.flat.id!!,
                    name = profile.name,
                    rent = profile.rent,
                    isPrivateRoom = profile.isPrivateRoom,
                    furnished = profile.furnished,
                    furnishedInfo = profile.furnishedInfo,
                    bio = profile.bio,
                    id = profile.id
                )
                return roomProfileService.update(roomProfileRequest, id)
            }
        }

        return null
    }
}