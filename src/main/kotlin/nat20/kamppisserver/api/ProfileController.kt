package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.service.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/profiles")
@Validated
class ProfileController(
    private val profileService: ProfileService,
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
        return profileService.updateProfile(profile, id)
    }
}