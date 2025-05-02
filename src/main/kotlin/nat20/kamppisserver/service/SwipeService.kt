package nat20.kamppisserver.service

import exception.DuplicateSwipeException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.ProfileStatus
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.repository.SwipeRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@Service
class SwipeService(
    private val matchService: MatchService,
    private val swipeRepository: SwipeRepository,
    private val profileRepository: ProfileRepository
) {

    @Transactional
    fun validateAndSwipe(swipeRequest: SwipeRequest, principal: Principal): ResponseEntity<SwipeResponse> {
        val swipingProfile: Profile = profileRepository.findByIdAndStatus(swipeRequest.swipingProfileId, ProfileStatus.ACTIVE)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id ${swipeRequest.swipingProfileId} not found")

        val email = principal.name
        if (!principalInSwipingProfile(email, swipingProfile)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Swiping profile $email cannot swipe on someone else's behalf")
        }

        val swipedProfile: Profile = profileRepository.findByIdAndStatus(swipeRequest.swipedProfileId, ProfileStatus.ACTIVE)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id ${swipeRequest.swipedProfileId} not found")

        if (swipingProfile.id == swipedProfile.id)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot swipe yourself you silly goose!")

        val response = swipe(
            swipingProfile,
            swipedProfile,
            swipeRequest.isRightSwipe
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    fun swipe(swipingProfile: Profile, swipedProfile: Profile, isRightSwipe: Boolean): SwipeResponse {
        // Check if an identical swipe already exists
        if (swipeRepository.existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(swipingProfile, swipedProfile, isRightSwipe)) {
            throw DuplicateSwipeException("Swipe already exists!")
        }

        val newSwipe = Swipe(
            swipingProfile = swipingProfile,
            swipedProfile = swipedProfile,
            isRightSwipe = isRightSwipe
        )
        swipeRepository.save(newSwipe)

        // Identical matches are prevented in the service
        if (isRightSwipe && hasMutualSwipe(swipingProfile, swipedProfile)) {
            val swipingUsers = swipingProfile.getUsersFromProfile()
            val swipedUsers = swipedProfile.getUsersFromProfile()
            matchService.createMatch(swipingUsers + swipedUsers)
            newSwipe.isMatch = true
        }
        return newSwipe.toSwipeResponse()
    }

    private fun hasMutualSwipe(swipingProfile: Profile, swipedProfile: Profile): Boolean {
        // Check if the swipedProfile has already right-swiped the swipingProfile
        return swipeRepository.existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(
            swipingProfile = swipedProfile, //REVERSED
            swipedProfile = swipingProfile, //REVERSED
            isRightSwipe = true
        )
    }

    fun principalInSwipingProfile(email: String, swipingProfile: Profile): Boolean {
        return email in swipingProfile.getUsersFromProfile().map { it.email }
    }

    fun findAll(): List<SwipeDTO> {
        return swipeRepository.findAll().map { it.toDTO() }
    }

}