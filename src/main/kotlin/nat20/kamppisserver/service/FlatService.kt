package nat20.kamppisserver.service

import exception.EntityNotFoundException
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import nat20.kamppisserver.domain.Flat
import nat20.kamppisserver.domain.FlatDTO
import nat20.kamppisserver.repository.FlatRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import kotlin.jvm.optionals.getOrNull

@Service
@Validated
class FlatService(
    private val flatRepository: FlatRepository,
    private val roomProfileRepository: RoomProfileRepository
) {

    fun findAll(): List<FlatDTO> {
        val flatList = flatRepository.findAll()
        return flatList.map { it.toDTO() }
    }

    fun findById(id: Long): FlatDTO {
        val flat = flatRepository.findById(id).getOrNull()
            ?: throw EntityNotFoundException("Flat with id $id not found")
        return flat.toDTO()
    }

    fun add(@Valid request: FlatDTO): FlatDTO {

        val flat = Flat(
            name = request.name,
            description = request.description,
            location = request.location,
            totalRoommates = request.totalRoommates,
            petHousehold = request.petHousehold,
            flatUtilities = request.flatUtilities,
            roomProfiles = mutableListOf(),
        )
        val addedFlat = flatRepository.save(flat)
        return addedFlat.toDTO()
    }

    @Transactional
    fun update(@Valid request: FlatDTO, id: Long): FlatDTO {
        val existingFlat = flatRepository.findById(id).getOrNull()
        ?: throw EntityNotFoundException("Flat with id $id not found")

        val roomProfiles = request.roomProfileIds?.map {
            roomProfileRepository.findByIdActive(it) ?: throw EntityNotFoundException("Room profile with id $it not found")
        }?.toMutableList() ?: mutableListOf()

        existingFlat.name = request.name
        existingFlat.description = request.description
        existingFlat.location = request.location
        existingFlat.totalRoommates = request.totalRoommates
        existingFlat.petHousehold = request.petHousehold
        existingFlat.flatUtilities = request.flatUtilities
        existingFlat.roomProfiles = roomProfiles

        val updatedFlat = flatRepository.save(existingFlat)

        return updatedFlat.toDTO()
    }

}