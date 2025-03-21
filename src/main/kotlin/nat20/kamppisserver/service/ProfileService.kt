package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserProfileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val roomProfileRepository: RoomProfileRepository,
    private val userProfileRepository: UserProfileRepository,
    private val profileRepository: ProfileRepository
) {

    /**
     * Returns all active Profiles as DTOs.
     */
    fun findAll(): List<ProfileDTO> {
        val roomProfiles = roomProfileRepository.findAllActive().map { it.toDTO() }
        val userProfiles = userProfileRepository.findAllActive().map { it.toDTO() }
        val profiles = roomProfiles + userProfiles
        return profiles
    }

    /**
     * Returns active Profile by id.
     *
     * @param id the id of the profile to be returned.
     * @return the profile with given id.
     */
    fun findById(id: Long): ProfileDTO? {
        val roomProfile = roomProfileRepository.findByIdActive(id)
        if (roomProfile != null) {
            return roomProfile.toDTO()
        }

        val userProfile = userProfileRepository.findByIdActive(id)
        if (userProfile != null) {
            return userProfile.toDTO()
        }

        throw EntityNotFoundException("Profile with id $id not found")
    }
}