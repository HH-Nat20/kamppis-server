package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDateTime

@Entity
@Table(name = "swipes")
class Swipe(
    @ManyToOne
    @JoinColumn(name = "swiping_user_id", nullable = false)
    @NotNull(message = "Swiping user cannot be null.")
    var swipingUser: User,

    @ManyToOne
    @JoinColumn(name = "swiped_user_id", nullable = false)
    @NotNull(message = "Swiped user cannot be null.")
    var swipedUser: User,

    var isRightSwipe: Boolean,

    @PastOrPresent(message = "Creation date cannot be in the future.")
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
    @NotNull val swipingUserId: Long,
    @NotNull val swipedUserId: Long,
    val isRightSwipe: Boolean
)

data class SwipeResponse(
    @NotNull val swipeId: Long,
    @NotNull val swipingUser: User,
    @NotNull val swipedUser: User,
    val isRightSwipe: Boolean,
    val isMatch: Boolean
)