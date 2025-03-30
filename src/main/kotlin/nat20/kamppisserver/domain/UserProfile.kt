package nat20.kamppisserver.domain

import jakarta.persistence.*
import nat20.kamppisserver.domain.enums.Cleanliness
import nat20.kamppisserver.domain.enums.Lifestyle
import nat20.kamppisserver.domain.enums.ProfileStatus
import nat20.kamppisserver.domain.enums.UserStatus
import java.time.LocalDateTime

@Entity
@Table(name = "user_profiles")
class UserProfile (

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    var user: User,

    @Enumerated(EnumType.STRING)
    var cleanliness: Cleanliness? = null,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Lifestyle::class)
    @CollectionTable(name = "user_profiles_lifestyle", joinColumns = [JoinColumn(name = "user_profile_id")])
    @Enumerated(EnumType.STRING)
    var lifestyle: MutableSet<Lifestyle>? = mutableSetOf(),

    bio: String = "Write bio here",

    photos: MutableList<ProfilePhoto> = mutableListOf()

) : Profile() {// Inherits id, bio, photos, and other attributes from Profile
    init {
        this.bio = bio // This block is needed if you want to initialize the bio during instance creation!
    }

    // Secondary constructor allows setting id explicitly (mainly for tests)
    constructor(id: Long, user: User, cleanliness: Cleanliness?, lifestyle: MutableSet<Lifestyle>?) : this(
        user, cleanliness, lifestyle
    ) {
        this.id = id
    }

    override fun toDTO(includeUserSummary: Boolean): UserProfileDTO {
        return UserProfileDTO(
            userId = user.id!!,
            user = if (includeUserSummary) user.toSummaryDTO() else null,
            bio = bio,
            cleanliness = cleanliness,
            lifestyle = lifestyle,
            photos = photos.map { it.toProfilePhotoDTO() }
                .toMutableList(),
            id = id
        )
    }

    fun toUserProfileDataDTO(): UserProfileDataDTO {
        return UserProfileDataDTO(
            bio = bio,
            cleanliness = cleanliness,
            lifestyle = lifestyle,
            photos = photos.map { it.toProfilePhotoDataDTO() }
                .toMutableList(),
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
        )
    }
}

data class UserProfileDTO(
    val userId: Long,
    val user: UserSummaryDTO? = null,
    val bio: String,
    val cleanliness: Cleanliness? = null,
    val lifestyle: MutableSet<Lifestyle>? = mutableSetOf(),
    val photos: MutableList<ProfilePhotoDTO> = mutableListOf(),
    val id: Long? = null
) : ProfileDTO

data class UserProfileRequest(
    val userId: Long,
    val bio: String? = "Write bio here",
    val cleanliness: Cleanliness? = null,
    val lifestyle: MutableSet<Lifestyle>? = mutableSetOf(),
    val photos: MutableList<ProfilePhotoDTO>? = mutableListOf(),
    val id: Long? = null
)

data class UserProfileDataDTO(
    // Only used by Copy of Data
    val bio: String,
    val cleanliness: Cleanliness? = null,
    val lifestyle: MutableSet<Lifestyle>? = mutableSetOf(),
    val photos: MutableList<ProfilePhotoDataDTO> = mutableListOf(),
    val status: ProfileStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
)
