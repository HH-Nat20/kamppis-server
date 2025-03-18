package nat20.kamppisserver.domain

import jakarta.persistence.*

@Entity
@Table(name = "profiles")
@Inheritance(strategy = InheritanceType.JOINED) // Ensures subtype tables
class Profile(

    @OneToOne
    @MapsId // Uses the same ID as User
    @JoinColumn(name = "id")
    var user: User,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var userProfile: UserProfile? = null,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var roomProfile: RoomProfile? = null,

    @Id
    var id: Long? = null, // Uses the same ID as User

)

fun toProfileDTO(profile: Profile): ProfileDTO {
    val profileDTO = ProfileDTO(
        id = profile.id,
        userId = profile.user.id ?: throw IllegalStateException("User ID is null"),
        userProfile = profile.userProfile?.let { toUserProfileDTO(it) },
        roomProfile = profile.roomProfile?.let { toRoomProfileDTO(it) },
    )
    return profileDTO
}

data class ProfileDTO(
    val id: Long?,
    val userId: Long,
    val userProfile: UserProfileDTO? = null,
    val roomProfile: RoomProfileDTO? = null,
)