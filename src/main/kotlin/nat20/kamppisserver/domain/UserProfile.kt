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
class UserProfile(
    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false)
    @NotNull(message = "Profile cannot be null.")
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    var cleanliness: Cleanliness? = null,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Lifestyle::class)
    @CollectionTable(name = "user_profiles_lifestyle", joinColumns = [JoinColumn(name = "user_profile_id")])
    @Enumerated(EnumType.STRING)
    var lifestyle: MutableList<Lifestyle>? = mutableListOf(),


    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @PastOrPresent(message = "Update date cannot be in the future.")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    @PastOrPresent(message = "Deletion date cannot be in the future.")
    var deletedAt: LocalDateTime? = null,

    @Id
    var id: Long,
)

fun toUserProfileDTO(userProfile: UserProfile): UserProfileDTO {
    val userProfileDTO = UserProfileDTO(
        cleanliness = userProfile.cleanliness,
        lifestyle = userProfile.lifestyle,
        id = userProfile.id
    )
    return userProfileDTO
}

data class UserProfileDTO(
    val cleanliness: Cleanliness? = null,
    val lifestyle: MutableList<Lifestyle>? = mutableListOf(),
    val id: Long? = null
)