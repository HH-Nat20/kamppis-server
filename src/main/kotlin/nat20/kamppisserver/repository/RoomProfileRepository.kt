package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.RoomProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RoomProfileRepository: JpaRepository<RoomProfile, Long> {
    @Query("SELECT rp FROM RoomProfile rp WHERE rp.deletedAt IS NULL")
    fun findAllActive(): List<RoomProfile>

    @Query("SELECT rp FROM RoomProfile rp WHERE rp.id = :id AND rp.deletedAt IS NULL")
    fun findByIdActive(@Param("id") id: Long): RoomProfile?
}