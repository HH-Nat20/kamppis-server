package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.enums.UserStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RoomProfileRepository: JpaRepository<RoomProfile, Long> {

    @EntityGraph(attributePaths = ["photos"])
    @Query("SELECT rp FROM RoomProfile rp WHERE rp.deletedAt IS NULL")
    fun findAllActive(): List<RoomProfile>

    @Query("SELECT rp FROM RoomProfile rp WHERE rp.id = :id AND rp.deletedAt IS NULL")
    fun findByIdActive(@Param("id") id: Long): RoomProfile?

    @Query("SELECT rp FROM RoomProfile rp JOIN rp.users u WHERE u.id = :id AND u.status = :status")
    fun findByUserIdAndStatus(@Param("id") id: Long, @Param("status") status: UserStatus): List<RoomProfile?>

    @Query("""
    SELECT DISTINCT rp.id, rp.rent, rp.is_private_room, rp.furnished, rp.furnished_info, rp.flat_id, p.bio, p.status, p.created_at, p.updated_at, p.deleted_at
    FROM room_profiles rp
    JOIN flats f ON rp.flat_id = f.id
    LEFT JOIN profiles p ON rp.id = p.id
    WHERE NOT EXISTS (
        SELECT 1
        FROM swipes s
        WHERE s.swiping_profile_id = :userProfileId
        AND s.swiped_profile_id = rp.id
    )
    AND (:maxRent IS NULL OR rp.rent <= :maxRent)
    AND (:hasPrivateRoom IS NULL OR rp.is_private_room = :hasPrivateRoom)
    AND (:maxRoommates IS NULL OR f.total_roommates <= :maxRoommates)
    AND (COALESCE(:locationPreferences) IS NULL OR f.location IN (:locationPreferences))
    """, nativeQuery = true)
    fun findRoomProfilesThatMeetCriteria(
        @Param("userProfileId") userProfileId: Long,
        @Param("maxRent") maxRent: Int?,
        @Param("hasPrivateRoom") hasPrivateRoom: Boolean?,
        @Param("maxRoommates") maxRoommates: Int?,
        @Param("locationPreferences") locationPreferences: List<String>?
    ): List<RoomProfile>
}