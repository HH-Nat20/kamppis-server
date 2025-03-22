package nat20.kamppisserver.api

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.service.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/profiles")
class ProfileController(private val profileService: ProfileService) {

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
     * Updates profile.
     */
    @PutMapping("/{id}")
    fun updateProfile(@PathVariable id: Long) {
        profileService.update(id)
    }
}