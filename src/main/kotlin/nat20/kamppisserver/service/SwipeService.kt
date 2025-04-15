package nat20.kamppisserver.service

import exception.DuplicateSwipeException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.SwipeRepository
import org.springframework.stereotype.Service

@Service
class SwipeService(
    private val matchService: MatchService,
    private val swipeRepository: SwipeRepository
) {
    @Transactional
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
            val swipingUsers = getUsersFromProfile(swipingProfile)
            val swipedUsers = getUsersFromProfile(swipedProfile)
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
        return email !in getUsersFromProfile(swipingProfile).map { it.email }
    }

    fun findAll(): List<SwipeDTO> {
        return swipeRepository.findAll().map { it.toDTO() }
    }

}