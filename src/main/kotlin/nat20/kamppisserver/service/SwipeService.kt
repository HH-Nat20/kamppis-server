package nat20.kamppisserver.service

import exception.DuplicateSwipeException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.SwipeDTO
import nat20.kamppisserver.domain.SwipeResponse
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.SwipeRepository
import org.springframework.stereotype.Service

@Service
class SwipeService(
    private val matchService: MatchService,
    private val swipeRepository: SwipeRepository
) {
    @Transactional
    fun swipe(swipingUser: User, swipedUser: User, isRightSwipe: Boolean): SwipeResponse {

        // Check if an identical swipe already exists
        if (swipeRepository.existsBySwipingUserAndSwipedUserAndIsRightSwipe(swipingUser, swipedUser, isRightSwipe)) {
            throw DuplicateSwipeException("Swipe already exists!")
        }

        val newSwipe = Swipe(
            swipingUser = swipingUser,
            swipedUser = swipedUser,
            isRightSwipe = isRightSwipe
        )
        swipeRepository.save(newSwipe)

        // Identical matches are prevented in the service
        if (isRightSwipe && hasMutualSwipe(swipingUser, swipedUser)) {
            matchService.createMatch(setOf(swipingUser, swipedUser))
            newSwipe.isMatch = true
        }
        return newSwipe.toSwipeResponse()
    }

    private fun hasMutualSwipe(swipingUser: User, swipedUser: User): Boolean {
        // Check if the swipedUser has already right-swiped the swipingUser
        return swipeRepository.existsBySwipingUserAndSwipedUserAndIsRightSwipe(
            swipingUser = swipedUser, //REVERSED
            swipedUser = swipingUser, //REVERSED
            isRightSwipe = true
        )
    }

    fun findAll(): List<SwipeDTO> {
        return swipeRepository.findAll().map { it.toDTO() }
    }

}