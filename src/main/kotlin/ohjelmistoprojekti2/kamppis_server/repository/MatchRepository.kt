package ohjelmistoprojekti2.kamppis_server.repository

import ohjelmistoprojekti2.kamppis_server.domain.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchRepository : JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m JOIN m.users u WHERE u.id = :userId")
    fun findAllByUserId(@Param("userId") userId: Long): List<Match>
}