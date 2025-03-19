package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import nat20.kamppisserver.domain.enums.Cleanliness
import nat20.kamppisserver.domain.enums.Lifestyle

import org.hibernate.annotations.UpdateTimestamp
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

) : Profile() {// Inherits id, bio, photos, and other attributes from Profile

    override fun toDTO(): UserProfileDTO {
        return UserProfileDTO(
            userId = user.id!!,
            bio = bio,
            cleanliness = cleanliness,
            lifestyle = lifestyle,
            photos = photos,
            id = id
        )
    }

}

data class UserProfileDTO(
    val userId: Long,
    val bio: String,
    val cleanliness: Cleanliness? = null,
    val lifestyle: MutableSet<Lifestyle>? = mutableSetOf(),
    val photos: MutableList<ProfilePhoto> = mutableListOf(),
    val id: Long? = null
) : ProfileDTO
