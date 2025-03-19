package nat20.kamppisserver.service

import exception.EntityNotFoundException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.RoomProfileDTO
import nat20.kamppisserver.repository.RoomProfileRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RoomProfileService(private val roomProfileRepository: RoomProfileRepository) {

    fun findAll(): List<RoomProfileDTO> {
        val roomProfileList = roomProfileRepository.findAllActive()
        return roomProfileList.map { it.toDTO() }
    }

    fun findById(id: Long): RoomProfileDTO {
        val roomProfile = roomProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("Room profile with id $id not found")

        return roomProfile.toDTO()
    }

    fun add(roomProfile: RoomProfile): RoomProfileDTO {
        // TODO: add appropriate validation to creating a RoomProfile
        val addedRoomProfile = roomProfileRepository.save(roomProfile)
        return roomProfile.toDTO()
    }

    @Transactional
    fun update(roomProfile: RoomProfileDTO, id: Long): RoomProfileDTO {
        val existingProfile = roomProfileRepository.findByIdActive(id)
            ?: throw EntityNotFoundException("Room profile with id $id not found")

        roomProfile.bio.let { existingProfile.bio = it }
        roomProfile.isPrivateRoom.let { existingProfile.isPrivateRoom = it }
        roomProfile.roomUtilities.let { existingProfile.roomUtilities = it }
        roomProfile.photos.let { existingProfile.photos.let { existingProfile.photos.addAll(it) } }
        roomProfile.roomUtilities.let { existingProfile.roomUtilities = it }
        roomProfile.rent.let { existingProfile.rent = it }

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
