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
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    var user: User,

    @Enumerated(EnumType.STRING)
    var cleanliness: Cleanliness? = null,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Lifestyle::class)
    @CollectionTable(name = "user_profiles_lifestyle", joinColumns = [JoinColumn(name = "user_profile_id")])
    @Enumerated(EnumType.STRING)
    var lifestyle: MutableList<Lifestyle>? = mutableListOf(),

) : Profile() // Inherits id, bio, and other attributes from Profile

fun toUserProfileDTO(userProfile: UserProfile): UserProfileDTO {
    val userProfileDTO = UserProfileDTO(
        userId = userProfile.user.id!!,
        bio = userProfile.bio,
        cleanliness = userProfile.cleanliness,
        lifestyle = userProfile.lifestyle,
        id = userProfile.id
    )
    return userProfileDTO
}

data class UserProfileDTO(
    val userId: Long,
    val bio: String,
    val cleanliness: Cleanliness? = null,
    val lifestyle: MutableList<Lifestyle>? = mutableListOf(),
    val id: Long? = null
)