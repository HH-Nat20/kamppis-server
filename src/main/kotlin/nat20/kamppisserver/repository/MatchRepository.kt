package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchRepository : JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m JOIN m.users u WHERE u.id = :userId")
    fun findAllByUserId(@Param("userId") userId: Long): List<Match>
}