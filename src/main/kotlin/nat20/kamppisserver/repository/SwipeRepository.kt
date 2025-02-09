package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface SwipeRepository : JpaRepository<Swipe, Long> {
    fun existsBySwipingUserAndSwipedUserAndIsRightSwipe(
        swipingUser: User,
        swipedUser: User,
        isRightSwipe: Boolean
    ): Boolean
}