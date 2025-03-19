package nat20.kamppisserver.api

import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.RoomProfileDTO
import nat20.kamppisserver.service.RoomProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/room-profiles")
class RoomProfileController(private val service: RoomProfileService) {

    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<RoomProfileDTO>> {
        return ResponseEntity.ok(service.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<RoomProfileDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    @PostMapping
    fun add(@RequestBody roomProfile: RoomProfile): ResponseEntity<RoomProfileDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(roomProfile))
    }

    @PutMapping("/{id}")
    fun update(@RequestBody roomProfile: RoomProfileDTO, @PathVariable("id") id: Long): ResponseEntity<RoomProfileDTO> {
        return ResponseEntity.ok(service.update(roomProfile, id))
    }
}