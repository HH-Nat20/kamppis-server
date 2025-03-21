package nat20.kamppisserver.domain

import jakarta.persistence.*
import nat20.kamppisserver.domain.enums.Cleanliness
import nat20.kamppisserver.domain.enums.Lifestyle

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

    bio: String = "Write bio here"

) : Profile() {// Inherits id, bio, photos, and other attributes from Profile
    init {
        this.bio = bio // This block is needed if you want to initialize the bio during instance creation!
    }

    override fun toDTO(): UserProfileDTO {
        return UserProfileDTO(
            userId = user.id!!,
            bio = bio,
            cleanliness = cleanliness,
            lifestyle = lifestyle,
            photos = photos
                .filter { it.deletedAt == null }
                .map { toProfilePhotoDTO(it) }
                .toMutableList(),
            id = id
        )
    }

}

data class UserProfileDTO(
    val userId: Long,
    val bio: String = "Write bio here",
    val cleanliness: Cleanliness? = null,
    val lifestyle: MutableSet<Lifestyle>? = mutableSetOf(),
    val photos: MutableList<ProfilePhotoDTO> = mutableListOf(),
    val id: Long? = null
) : ProfileDTO
