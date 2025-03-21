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
     * Returns all Profiles as DTOs.
     */
    fun findAll(): List<ProfileDTO> {
        val roomProfiles = roomProfileRepository.findAll().map { it.toDTO() }
        val userProfiles = userProfileRepository.findAll().map { it.toDTO() }
        val profiles = roomProfiles + userProfiles
        return profiles
    }

    /**
     * Returns Profile by id.
     *
     * @param id the id of the profile to be returned.
     * @return the profile with given id.
     */
    fun findById(id: Long): ProfileDTO? {
        val profile = profileRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("Profile with id $id not found")

        return profile.toDTO()
    }

}