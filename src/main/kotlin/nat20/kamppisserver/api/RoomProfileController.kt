package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.RoomProfileDTO
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.RoomProfileService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Page
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
    fun findRoomProfilesThatMeetCriteria(@RequestParam(defaultValue = "0") page: Int, @RequestParam size: Int, @PathVariable userId: Long): ResponseEntity<Page<RoomProfileDTO>> {
        val pageable = PageRequest.of(page, size)
        val results = queryService.findRoomProfilesThatMeetCriteria(pageable, userId)

        return if (results.isEmpty) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(results)
        }

    }

    /**
     * Finds all the user profiles that have swiped a room profile.
     *
     * @param id the id of the room profile whose swipers (user profiles) we want to find.
     * @return ResponseEntity with a list of user profiles and status code 200 OK if user profiles have swiped the room profile.
     * @return ResponseEntity with status code 204 NO_CONTENT if no user profiles have swiped the room profile.
     */
    @GetMapping("/{roomProfileId}/swipersquery")
    fun findUserProfilesWhoHaveSwipedUsersRoom(@RequestParam(defaultValue = "0") page: Int, @RequestParam size: Int, @PathVariable roomProfileId: Long): ResponseEntity<Page<UserProfileDTO>>{
        val pageable = PageRequest.of(page, size)
        val results = queryService.findUserProfilesThatHaveSwipedRoomProfile(pageable, roomProfileId)

        return if (results.isEmpty) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(results)
        }

    }
}