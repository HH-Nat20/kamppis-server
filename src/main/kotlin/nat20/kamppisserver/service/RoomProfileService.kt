package nat20.kamppisserver.service

import exception.EntityNotFoundException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.RoomProfileDTO
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.repository.FlatRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RoomProfileService(
    private val roomProfileRepository: RoomProfileRepository,
    private val userRepository: UserRepository,
    private val flatRepository: FlatRepository) {

    fun findAll(): List<RoomProfileDTO> {
        val roomProfileList = roomProfileRepository.findAllActive()
        return roomProfileList.map { it.toDTO() }
    }

    fun findById(id: Long): RoomProfileDTO {
        val roomProfile = roomProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("Room profile with id $id not found")

        return roomProfile.toDTO()
    }

    fun add(request: RoomProfileRequest): RoomProfileDTO {
        val users = request.userIds.map { userRepository.findById(it).get() }.toMutableList()
        val flat = request.flatId.let { flatRepository.findById(it).get() }

        val roomProfile = RoomProfile(
            users = users,
            flat = flat,
            rent = request.rent,
            isPrivateRoom = request.isPrivateRoom,
            roomUtilities = request.roomUtilities,
            bio = request.bio,
        )

        val addedRoomProfile = roomProfileRepository.save(roomProfile)
        return addedRoomProfile.toDTO()
    }

    @Transactional
    fun update(request: RoomProfileRequest, id: Long): RoomProfileDTO {
        val existingProfile = roomProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("Room profile with id $id not found")

        val users = request.userIds.map { userRepository.findById(it).get() }.toMutableList()

        val flat = request.flatId.let { flatRepository.findById(it).get() }

        existingProfile.flat = flat
        existingProfile.users = users
        request.bio.let { existingProfile.bio = it }
        request.isPrivateRoom.let { existingProfile.isPrivateRoom = it }
        request.roomUtilities.let { existingProfile.roomUtilities = it }
        request.roomUtilities.let { existingProfile.roomUtilities = it }
        request.rent.let { existingProfile.rent = it }

        existingProfile.updatedAt = LocalDateTime.now()

        val updatedProfile = roomProfileRepository.save(existingProfile)

        return updatedProfile.toDTO()
    }

    fun delete(id: Long): Boolean {
        val roomProfile = roomProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("Room profile with id $id not found")

        roomProfile.deletedAt = LocalDateTime.now()
        roomProfileRepository.save(roomProfile)

        return true
    }
}
