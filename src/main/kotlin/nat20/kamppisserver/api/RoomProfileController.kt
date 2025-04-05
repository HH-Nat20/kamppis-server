package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.RoomProfileDTO
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.RoomProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/room-profiles")
@Validated
class RoomProfileController(private val service: RoomProfileService,
                            private val queryService: QueryService) {

    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<RoomProfileDTO>> {
        return ResponseEntity.ok(service.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<RoomProfileDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    @PostMapping
    fun add(@Valid @RequestBody roomProfile: RoomProfileRequest): ResponseEntity<RoomProfileDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(roomProfile))
    }

    @PutMapping("/{id}")
    fun update(@Valid @RequestBody roomProfile: RoomProfileRequest, @PathVariable("id") id: Long): ResponseEntity<RoomProfileDTO> {
        return ResponseEntity.ok(service.update(roomProfile, id))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    /**
     * Finds all the room profiles that match the user's search criteria.
     *
     * @param id the id of the user who initiated the query.
     * @return ResponseEntity with list of room profiles and status code 200 OK if room profiles have been found.
     * @return ResponseEntity with status code 204 NO_CONTENT if no room profiles have been found.
     */
    @GetMapping("/{userId}/query")
    fun findRoomProfilesThatMeetCriteria(@PathVariable userId: Long): ResponseEntity<List<RoomProfileDTO>> {
        val roomProfileList: List<RoomProfileDTO> = queryService.findRoomProfilesThatMeetCriteria(userId)

        if (roomProfileList.isEmpty()) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }

        return ResponseEntity(roomProfileList, HttpStatus.OK)
    }
}