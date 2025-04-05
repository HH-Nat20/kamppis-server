package nat20.kamppisserver.service

import exception.EntityNotFoundException
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.RoomProfileDTO
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.repository.FlatRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

@Service
@Validated
class RoomProfileService(
    private val roomProfileRepository: RoomProfileRepository,
    private val userRepository: UserRepository,
    private val flatRepository: FlatRepository,
    private val flatService: FlatService
) {

    fun findAll(): List<RoomProfileDTO> {
        val roomProfileList = roomProfileRepository.findAllActive()
        return roomProfileList.map { it.toDTO(includeUserSummary = true) }
    }

    fun findById(id: Long): RoomProfileDTO {
        val roomProfile = roomProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("Room profile with id $id not found")

        return roomProfile.toDTO(includeUserSummary = true)
    }

    fun add(@Valid request: RoomProfileRequest): RoomProfileDTO {
        val users = request.userIds.map { userRepository.findById(it).get() }.toMutableList()
        val flat = request.flatId.let { flatRepository.findById(it).get() }

        val roomProfile = RoomProfile(
            users = users,
            flat = flat,
            rent = request.rent,
            isPrivateRoom = request.isPrivateRoom,
            furnished = request.furnished,
            furnishedInfo = request.furnishedInfo,
            bio = request.bio,
        )

        val addedRoomProfile = roomProfileRepository.save(roomProfile)

        flatService.updatePetHouseholdStatus(roomProfile.flat.id!!, roomProfile.id!!)

        return addedRoomProfile.toDTO(includeUserSummary = true)
    }

    @Transactional
    fun update(@Valid request: RoomProfileRequest, id: Long): RoomProfileDTO {
        val existingProfile = roomProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("Room profile with id $id not found")

        val users = request.userIds.map { userRepository.findById(it).get() }.toMutableList()

        val flat = request.flatId.let { flatRepository.findById(it).get() }

        existingProfile.flat = flat
        existingProfile.users = users
        request.bio.let { existingProfile.bio = it }
        request.isPrivateRoom.let { existingProfile.isPrivateRoom = it }
        request.furnished.let {existingProfile.furnished = it }
        request.furnishedInfo.let {existingProfile.furnishedInfo = it }
        request.rent.let { existingProfile.rent = it }

        existingProfile.updatedAt = LocalDateTime.now()

        val updatedProfile = roomProfileRepository.saveAndFlush(existingProfile)

        flatService.updatePetHouseholdStatus(updatedProfile.flat.id!!, updatedProfile.id!!)

        return updatedProfile.toDTO(includeUserSummary = true)
    }

    fun delete(id: Long): Boolean {
        val roomProfile = roomProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("Room profile with id $id not found")

        roomProfile.deletedAt = LocalDateTime.now()
        roomProfileRepository.save(roomProfile)

        flatService.updatePetHouseholdStatus(roomProfile.flat.id!!, roomProfile.id!!)

        return true
    }
}
