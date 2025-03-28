package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDateTime

@Entity
@Table(name = "swipes")
class Swipe(
    @ManyToOne
    @JoinColumn(name = "swiping_profile_id", nullable = false)
    @NotNull(message = "Swiping profile cannot be null.")
    var swipingProfile: Profile,

    @ManyToOne
    @JoinColumn(name = "swiped_profile_id", nullable = false)
    @NotNull(message = "Swiped profile cannot be null.")
    var swipedProfile: Profile,

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
            swipingProfile = swipingProfile.toDTO(includeUserSummary = true),
            swipedProfile = swipedProfile.toDTO(includeUserSummary = true),
            isRightSwipe = isRightSwipe,
            isMatch = isMatch
        )
    }

    fun toDTO(): SwipeDTO {
        return SwipeDTO(
            id = id,
            swipingProfile = swipingProfile.toDTO(includeUserSummary = true),
            swipedProfile = swipedProfile.toDTO(includeUserSummary = true),
            isRightSwipe = isRightSwipe,
            isMatch = isMatch,
            createdAt = createdAt,
        )
    }
}

data class SwipeRequest(
    @NotNull val swipingProfileId: Long,
    @NotNull val swipedProfileId: Long,
    val isRightSwipe: Boolean
)

data class SwipeResponse(
    @NotNull val swipeId: Long,
    @NotNull val swipingProfile: ProfileDTO,
    @NotNull val swipedProfile: ProfileDTO,
    val isRightSwipe: Boolean,
    val isMatch: Boolean
)

data class SwipeDTO(
    val id: Long? = null,
    @NotNull val swipingProfile: ProfileDTO,
    @NotNull val swipedProfile: ProfileDTO,
    val isRightSwipe: Boolean,
    val isMatch: Boolean,
    val createdAt: LocalDateTime
)