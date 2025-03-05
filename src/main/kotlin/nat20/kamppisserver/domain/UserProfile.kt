package nat20.kamppisserver.domain

import jakarta.persistence.*
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Cleanliness
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.Lifestyle
import nat20.kamppisserver.domain.enums.MaxRent

import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Entity class for UserProfile.
 * @OneToOne relationship to User.
 * @OneToMany relationship to UserHabit.
 * @OneToMany relationship to UserInterest.
 */
@Entity
@Table(name = "user_profiles")
class UserProfile(
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    var user: User,

    var firstName: String,
    var lastName: String,
    var dateOfBirth: LocalDate,

    @Enumerated(EnumType.STRING)
    var gender: Gender,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "userProfile", orphanRemoval = true)
    var userPhotos: MutableList<UserPhoto>? = mutableListOf(),

    var bio: String? = null,
    var minAgePreference: Int? = null,
    var maxAgePreference: Int? = null,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Gender::class)
    @CollectionTable(name = "user_profiles_genders", joinColumns = [JoinColumn(name = "user_profile_id")])
    @Enumerated(EnumType.STRING)
    var preferredGenders: MutableList<Gender>? = mutableListOf(Gender.NOT_IMPORTANT),

    @ElementCollection(fetch = FetchType.EAGER, targetClass = City::class)
    @CollectionTable(name = "user_profiles_locations", joinColumns = [JoinColumn(name = "user_profile_id")])
    @Enumerated(EnumType.STRING)
    var preferredLocations: MutableList<City>? = mutableListOf(),

    @Enumerated(EnumType.STRING) // Remove EnumType.STRING if we want to compare users' maxRent values by enum ordinal values
    var maxRent: MaxRent,

    @Enumerated(EnumType.STRING)
    var cleanliness: Cleanliness,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Lifestyle::class)
    @CollectionTable(name = "user_profiles_lifestyle", joinColumns = [JoinColumn(name = "user_profile_id")])
    @Enumerated(EnumType.STRING)
    var lifestyle: MutableList<Lifestyle>? = mutableListOf(),

    /*
    * ADD FIELDS HERE AS REQUIRED
    * */

    var createdAt: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    @UpdateTimestamp
    var deletedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)

fun toUserProfileDTO(userProfile: UserProfile): UserProfileDTO {
    val userProfileDTO: UserProfileDTO = UserProfileDTO(
        userId = userProfile.user.id!!,
        firstName = userProfile.firstName,
        lastName = userProfile.lastName,
        //TODO: age = ChronoUnit.YEARS.between(userProfile.dateOfBirth, LocalDate.now()),
        age = ChronoUnit.YEARS.between(userProfile.dateOfBirth, LocalDate.of(2025, 2, 21)),
        gender = userProfile.gender,
        userPhotos = userProfile.userPhotos?.map {it.id}?.toMutableList(),
        bio = userProfile.bio,
        preferredLocations = userProfile.preferredLocations,
        maxRent = userProfile.maxRent,
        cleanliness = userProfile.cleanliness,
        lifestyle = userProfile.lifestyle,
        id = userProfile.id!!
    )

    return userProfileDTO
}

data class UserProfileDTO(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val age: Long,
    val gender: Gender,
    val userPhotos: MutableList<Long?>?,
    val bio: String?,
    val preferredLocations: MutableList<City>?,
    val maxRent: MaxRent,
    val cleanliness: Cleanliness,
    val lifestyle: MutableList<Lifestyle>?,
    val id: Long
)