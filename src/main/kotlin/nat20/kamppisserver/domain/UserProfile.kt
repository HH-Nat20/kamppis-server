package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.PastOrPresent
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Cleanliness
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.Lifestyle
import nat20.kamppisserver.domain.enums.MaxRent

import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "user_profiles")
class UserProfile(
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @NotNull(message = "User cannot be null.")
    var user: User,

    @NotEmpty(message = "First name cannot be empty.")
    var firstName: String,

    @NotEmpty(message = "Last name cannot be empty.")
    var lastName: String,

    @Past(message = "Date of birth cannot be in the future.")
    var dateOfBirth: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Gender cannot be null.")
    var gender: Gender,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "userProfile", orphanRemoval = true, fetch = FetchType.EAGER,)
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

    @Enumerated(EnumType.STRING) // TODO: Remove EnumType.STRING if we want to compare users' maxRent values by enum ordinal values
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

    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @PastOrPresent(message = "Update date cannot be in the future.")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    @PastOrPresent(message = "Deletion date cannot be in the future.")
    var deletedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)

fun toUserProfileDTO(userProfile: UserProfile): UserProfileDTO {
    val userProfileDTO: UserProfileDTO = UserProfileDTO(
        //userId = userProfile.user.id!!,
        firstName = userProfile.firstName,
        lastName = userProfile.lastName,
        age = ChronoUnit.YEARS.between(userProfile.dateOfBirth, LocalDate.now()),
        gender = userProfile.gender,
        userPhotos = userProfile.userPhotos?.map { toUserPhotoDTO(it) }?.toMutableList(),
        bio = userProfile.bio,
        minAgePreference = userProfile.minAgePreference,
        maxAgePreference = userProfile.maxAgePreference,
        preferredGenders = userProfile.preferredGenders,
        preferredLocations = userProfile.preferredLocations,
        maxRent = userProfile.maxRent,
        cleanliness = userProfile.cleanliness,
        lifestyle = userProfile.lifestyle,
        id = userProfile.id
    )

    return userProfileDTO
}

data class UserProfileDTO(
    //val userId: Long,
    @NotEmpty val firstName: String,
    @NotEmpty val lastName: String,
    val age: Long? = null,
    @NotNull val gender: Gender,
    val userPhotos: MutableList<UserPhotoDTO?>? = null,
    val bio: String? = "",
    val minAgePreference: Int? = null,
    val maxAgePreference: Int? = null,
    val preferredGenders: MutableList<Gender>? = null,
    val preferredLocations: MutableList<City>? = null,
    val maxRent: MaxRent,
    val cleanliness: Cleanliness,
    val lifestyle: MutableList<Lifestyle>? = null,
    val id: Long? = null
)