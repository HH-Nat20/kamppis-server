package nat20.kamppisserver.dto

data class SwipeRequest(
    val swipingUserId: Long,
    val swipedUserId: Long,
    val isRightSwipe: Boolean = true
)
