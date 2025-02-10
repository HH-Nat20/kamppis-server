package nat20.kamppisserver.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "swipes")
class Swipe(
    @ManyToOne var swipingUser: User,
    @ManyToOne var swipedUser: User,
    var isRightSwipe: Boolean,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var isMatch: Boolean = false,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    fun toSwipeResponse(): SwipeResponse {
        return SwipeResponse(
            swipeId = id ?: throw IllegalStateException("Swipe ID is null"),
            swipingUser = swipingUser,
            swipedUser = swipedUser,
            isRightSwipe = isRightSwipe,
            isMatch = isMatch
        )
    }
}

data class SwipeRequest(
    val swipingUserId: Long,
    val swipedUserId: Long,
    val isRightSwipe: Boolean = true
)

data class SwipeResponse(
    val swipeId: Long,
    val swipingUser: User,
    val swipedUser: User,
    val isRightSwipe: Boolean,
    val isMatch: Boolean
)