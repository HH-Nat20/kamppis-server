package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchRepository : JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m JOIN m.users u WHERE u.id = :userId")
    fun findAllByUserId(@Param("userId") userId: Long): List<Match>
        
    /**
    * Get all user profiles that have matched with user with id :id
    */
    @Query(value = """
        SELECT * FROM \"user_profiles\" WHERE \"id\" IN (
            SELECT \"user_id\" FROM \"matches_users\" WHERE \"match_id\" IN (
                SELECT \"match_id\" FROM \"matches_users\" WHERE \"user_id\" = :id
            ) AND \"user_id\" != :id
        )
    """, nativeQuery = true)
    fun findUserProfilesThatMatchWithUser(
        @Param("id") id: Long?
    ): MutableIterable<UserProfile>

    // Needed for fetching users to avoid LazyInitializationException
    @Query("SELECT m FROM Match m JOIN FETCH m.users WHERE m.id = :matchId")
    fun findByIdWithUsers(@Param("matchId") matchId: Long): Match?

}