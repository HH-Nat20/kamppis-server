package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Profile
import nat20.kamppisserver.domain.Swipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SwipeRepository : JpaRepository<Swipe, Long> {
    fun existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(
        swipingProfile: Profile,
        swipedProfile: Profile,
        isRightSwipe: Boolean
    ): Boolean

    @Query("""
        SELECT swiping_profile_id
        FROM swipes s
        JOIN profiles p ON p.id = s.swiping_profile_id
        WHERE s.swiped_profile_id = :roomProfileId
        AND s.is_right_swipe IS TRUE
        AND p.status = 'ACTIVE'
    """, nativeQuery = true)
    fun findUseProfilesThatHaveSwipedRoomProfile(
        @Param("roomProfileId") roomProfileId: Long
    ): MutableList<Long>
}