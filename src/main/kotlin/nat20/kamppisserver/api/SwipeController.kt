package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.ProfileStatus
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.service.SwipeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/swipes")
@Validated
class SwipeController(private val swipeService: SwipeService,
    private val profileRepository: ProfileRepository
) {

    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<SwipeDTO>> {
        val swipes = swipeService.findAll()
        return ResponseEntity.ok().body(swipes)
    }

    @PostMapping
    fun swipe(@Valid @RequestBody swipeRequest: SwipeRequest): ResponseEntity<SwipeResponse> {
        val swipingProfile: Profile = profileRepository.findByIdAndStatus(swipeRequest.swipingProfileId, ProfileStatus.ACTIVE)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id ${swipeRequest.swipingProfileId} not found")
        val swipedProfile: Profile = profileRepository.findByIdAndStatus(swipeRequest.swipedProfileId, ProfileStatus.ACTIVE)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id ${swipeRequest.swipedProfileId} not found")

        if (swipingProfile.id == swipedProfile.id)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot swipe yourself you silly goose!")

        val response = swipeService.swipe(
            swipingProfile,
            swipedProfile,
            swipeRequest.isRightSwipe
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }


}