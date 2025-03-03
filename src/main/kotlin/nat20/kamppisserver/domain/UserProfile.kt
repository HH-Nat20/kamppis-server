package nat20.kamppisserver.domain

import jakarta.persistence.*
import nat20.kamppisserver.util.JsonStringListConverter
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

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "userProfile", orphanRemoval = true)
    var userHabits: MutableList<UserHabit>? = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "userProfile", orphanRemoval = true)
    var userInterests: MutableList<UserInterest>? = mutableListOf(),

    var bio: String? = null,
    var minAgePreference: Int? = null,
    var maxAgePreference: Int? = null,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Gender::class)
    @Enumerated(EnumType.STRING)
    var preferredGenders: MutableList<Gender>? = mutableListOf(Gender.NOT_IMPORTANT),

    @ElementCollection(fetch = FetchType.EAGER, targetClass = City::class)
    @CollectionTable(name = "user_profiles_locations", joinColumns = [JoinColumn(name = "user_profile_id")])
    @Enumerated(EnumType.STRING)
    //@Convert(converter = JsonStringListConverter::class)
    var preferredLocations: MutableList<City>? = mutableListOf(),

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
        bio = userProfile.bio,
        preferredLocations = userProfile.preferredLocations,
        userPhotos = userProfile.userPhotos,
        userHabits = userProfile.userHabits,
        userInterests = userProfile.userInterests,
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
    val userPhotos: MutableList<UserPhoto>?,
    val userHabits: MutableList<UserHabit>?,
    val userInterests: MutableList<UserInterest>?,
    val bio: String?,
    val preferredLocations: MutableList<City>?,
    val id: Long
)