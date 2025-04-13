package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Profile
import nat20.kamppisserver.domain.Swipe
import org.springframework.data.jpa.repository.JpaRepository

interface SwipeRepository : JpaRepository<Swipe, Long> {
    fun existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(
        swipingProfile: Profile,
        swipedProfile: Profile,
        isRightSwipe: Boolean
    ): Boolean
}