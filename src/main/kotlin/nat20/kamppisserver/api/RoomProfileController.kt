package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.RoomProfileDTO
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.service.RoomProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/room-profiles")
@Validated
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
    fun add(@Valid @RequestBody roomProfile: RoomProfileRequest): ResponseEntity<RoomProfileDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(roomProfile))
    }

    @PutMapping("/{id}")
    fun update(@Valid @RequestBody roomProfile: RoomProfileRequest, @PathVariable("id") id: Long): ResponseEntity<RoomProfileDTO> {
        return ResponseEntity.ok(service.update(roomProfile, id))
    }
}