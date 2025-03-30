package nat20.kamppisserver.api

import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.service.ProfileService
import nat20.kamppisserver.service.RoomProfileService
import nat20.kamppisserver.service.UserProfileService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/profiles")
@Validated
class ProfileController(private val profileService: ProfileService,
                        private val roomProfileRepository: RoomProfileRepository,
                        private val userProfileRepository: UserProfileRepository,
                        private val roomProfileService: RoomProfileService,
                        private val userProfileService: UserProfileService
) {

    /**
     * Returns all Profiles as DTOs.
     */
    @GetMapping
    fun findAll(): ResponseEntity<List<ProfileDTO>> {
        return ResponseEntity.ok(profileService.findAll())
    }

    /**
     * Returns Profile by id.
     */
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<ProfileDTO> {
        return ResponseEntity.ok(profileService.findById(id))
    }

    /**
     * Checks given active Profile and sends it through to the appropriate method.
     *
     * @param id the id of the profile to be updated.
     * @return the updated profile.
     */
    @PutMapping("/{id}")
    fun updateProfile(@Valid @RequestBody profile: ProfileDTO, @PathVariable id: Long): ResponseEntity<ProfileDTO> {
        val userProfile = userProfileRepository.findByIdActive(id)
        val roomProfile = roomProfileRepository.findByIdActive(id)

        when {
            userProfile != null && profile is UserProfileDTO -> {
                val userProfileRequest = UserProfileRequest(
                    userId = profile.userId,
                    bio = profile.bio,
                    cleanliness = profile.cleanliness,
                    lifestyle = profile.lifestyle,
                    photos = profile.photos,
                    id = profile.id
                )
                return ResponseEntity.ok(userProfileService.update(userProfileRequest, id))
            }

            roomProfile != null && profile is RoomProfileDTO -> {
                val roomProfileRequest = RoomProfileRequest(
                    userIds = profile.userIds,
                    flatId = profile.flat.id!!,
                    rent = profile.rent,
                    isPrivateRoom = profile.isPrivateRoom,
                    roomUtilities = profile.roomUtilities,
                    bio = profile.bio,
                    id = profile.id
                )
                return ResponseEntity.ok(roomProfileService.update(roomProfileRequest, id))
            }
            else -> return ResponseEntity.notFound().build()
        }
    }
}