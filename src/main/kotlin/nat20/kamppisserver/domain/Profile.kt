package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import nat20.kamppisserver.domain.enums.ProfileType

@Entity
@Table(name = "profiles")
@Inheritance(strategy = InheritanceType.JOINED) // Ensures subtype tables
class Profile(
    @ManyToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @NotNull(message = "User cannot be null.")
    var user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_type", nullable = false)
    @NotNull(message = "Profile type cannot be null.")
    var profileType: ProfileType,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var userProfile: UserProfile? = null,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var roomProfile: RoomProfile? = null,

    @NotEmpty(message = "Bio cannot be empty.")
    var bio: String = "Write your bio here",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)

fun toProfileDTO(profile: Profile): ProfileDTO {
    val profileDTO = ProfileDTO(
        userId = profile.user.id ?: throw IllegalStateException("User ID is null"),
        profileType = profile.profileType,
        bio = profile.bio,
        userProfile = profile.userProfile?.let { toUserProfileDTO(it) },
        roomProfile = profile.roomProfile?.let { toRoomProfileDTO(it) },
        id = profile.id
    )
    return profileDTO
}

data class ProfileDTO(
    val userId: Long,
    val profileType: ProfileType,
    val bio: String? = null,
    val userProfile: UserProfileDTO? = null,
    val roomProfile: RoomProfileDTO? = null,
    val id: Long? = null
)