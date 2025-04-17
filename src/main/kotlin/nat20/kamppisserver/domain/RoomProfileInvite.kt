package nat20.kamppisserver.domain

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "room_profile_invites")
class RoomProfileInvite (

    @NotNull
    @Column(name = "room_profile_id")
    var roomProfileId: Long,

    @NotNull
    @Column(name = "room_profile_invite_token", nullable = false)
    var roomProfileInviteToken: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val expiresAt: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

fun toInviteResponse(roomProfileInvite: RoomProfileInvite): InviteResponse {
    val inviteResponse = InviteResponse(
        inviteToken = roomProfileInvite.roomProfileInviteToken,
        expiresAt = roomProfileInvite.expiresAt,
        message = null
    )

    return inviteResponse
}

data class InviteResponse(
    val inviteToken: String,
    val expiresAt: LocalDateTime,
    var message: String?
)
