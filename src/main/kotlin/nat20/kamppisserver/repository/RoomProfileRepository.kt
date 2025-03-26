package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.RoomProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface RoomProfileRepository: JpaRepository<RoomProfile, Long> {

    @EntityGraph(attributePaths = ["photos"])
    @Query("SELECT rp FROM RoomProfile rp WHERE rp.deletedAt IS NULL")
    fun findAllActive(): List<RoomProfile>

    @Query("SELECT rp FROM RoomProfile rp WHERE rp.id = :id AND rp.deletedAt IS NULL")
    fun findByIdActive(@Param("id") id: Long): RoomProfile?

    @Query("""
    SELECT DISTINCT rp.id, rp.rent, rp.is_private_room, rp.flat_id, p.bio, p.status, p.created_at, p.updated_at, p.deleted_at
    FROM room_profiles rp
    JOIN flats f ON rp.flat_id = f.id
    LEFT JOIN profiles p ON rp.id = p.id
    WHERE NOT EXISTS (
        SELECT 1
        FROM swipes s
        WHERE s.swiping_profile_id = :id
        AND s.swiped_profile_id = rp.id
    )
    AND (rp.rent <= :maxRent)
    AND (:hasPrivateRoom IS NULL OR rp.is_private_room = :hasPrivateRoom)
    AND (f.total_roommates <= :maxRoommates)
    AND (f.location IN (:locationPreferences))
    """, nativeQuery = true)
    fun findRoomProfilesThatMeetCriteria(
        @Param("id") id: Long,
        @Param("maxRent") maxRent: Int,
        @Param("hasPrivateRoom") hasPrivateRoom: Boolean?,
        @Param("maxRoommates") maxRoommates: Int,
        @Param("locationPreferences") locationPreferences: MutableList<String>
    ): MutableList<RoomProfile>
}