package nat20.kamppisserver.service

import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.SwipeRepository
import org.springframework.stereotype.Service

@Service
class SwipeService(
    private val matchService: MatchService,
    private val swipeRepository: SwipeRepository
) {
    fun swipe(swipingUser: User, swipedUser: User, isRightSwipe: Boolean): Swipe {

        // TODO: forbid multiple identical swipes
        val newSwipe = Swipe(
            swipingUser = swipingUser,
            swipedUser = swipedUser,
            isRightSwipe = isRightSwipe
        )
        swipeRepository.save(newSwipe)

        // TODO: forbid multiple identical matches
        if (isRightSwipe && hasMutualSwipe(swipingUser, swipedUser)) {
            matchService.createMatch(swipingUser, swipedUser)
        }
        return newSwipe
    }

    private fun hasMutualSwipe(swipingUser: User, swipedUser: User): Boolean {
        // Check if the swipedUser has already right-swiped the swipingUser
        return swipeRepository.existsBySwipingUserAndSwipedUserAndIsRightSwipe(
            swipingUser = swipedUser, //REVERSED
            swipedUser = swipingUser, //REVERSED
            isRightSwipe = true
        )
    }

    fun findAll(): MutableIterable<Swipe> {
        return swipeRepository.findAll()
    }

}