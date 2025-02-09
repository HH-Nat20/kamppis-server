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
        val newSwipe = Swipe(
            swipingUser = swipingUser,
            swipedUser = swipedUser,
            isRightSwipe = isRightSwipe
        )
        swipeRepository.save(newSwipe)

        if (isRightSwipe && hasMutualSwipe(swipingUser, swipedUser)) {
            matchService.createMatch(swipingUser, swipedUser)
        }
        return newSwipe
    }

    private fun hasMutualSwipe(swipingUser: User, swipedUser: User): Boolean {
        return swipeRepository.existsBySwipingUserAndSwipedUserAndIsRightSwipe(
            swipingUser = swipingUser,
            swipedUser = swipedUser,
            isRightSwipe = true
        )
    }

    fun findAll(): MutableIterable<Swipe> {
        return swipeRepository.findAll()
    }

}