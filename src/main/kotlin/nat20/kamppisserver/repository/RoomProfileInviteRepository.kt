package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.RoomProfileInvite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface RoomProfileInviteRepository: JpaRepository<RoomProfileInvite, Long> {


    @Query("""
        SELECT rpi.*
        FROM room_profile_invites rpi
        WHERE rpi.room_profile_id = :roomProfileId
        AND rpi.expires_at > :dateTimeNow
    """, nativeQuery = true)
    fun findActiveInviteByRoomProfileId(
        @Param("roomProfileId") roomProfileId: Long,
        @Param("dateTimeNow") dateTimeNow: LocalDateTime
    ): RoomProfileInvite?

    @Query("""
        SELECT rpi.*
        FROM room_profile_invites rpi
        WHERE rpi.room_profile_invite_token = :roomProfileInviteToken
    """, nativeQuery = true)
    fun findInviteByInviteToken(
        @Param("roomProfileInviteToken") roomProfileInviteToken: String): RoomProfileInvite
}