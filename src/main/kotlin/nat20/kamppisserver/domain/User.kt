package nat20.kamppisserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.*
import nat20.kamppisserver.domain.enums.*
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "users")
class User(
    @Column(name = "first_name")
    @NotEmpty(message = "First name cannot be empty.")
    var firstName: String,

    @Column(name = "last_name")
    @NotEmpty(message = "Last name cannot be empty.")
    var lastName: String,

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Invalid email format.")
    var email: String,

    @Column(name = "date_of_birth")
    @Past(message = "Date of birth cannot be in the future.")
    var dateOfBirth: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Gender cannot be null.")
    var gender: Gender,

    @Column(name = "looking_for")
    @Enumerated(EnumType.STRING)
    var lookingFor: LookingFor? = LookingFor.OTHER_USER_PROFILES_OR_ROOM_PROFILES,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus? = UserStatus.ACTIVE,

    // Specifically for use in chat
    @Column(name = "is_online")
    var isOnline: Boolean? = false,

    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @PastOrPresent(message = "Update date cannot be in the future.")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    @PastOrPresent(message = "Deletion date cannot be in the future.")
    var deletedAt: LocalDateTime? = null,

    @JsonIgnore
    @ManyToMany(mappedBy = "users") // This makes it bidirectional
    var matches: MutableSet<Match>? = mutableSetOf(),

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var userProfile: UserProfile? = null,

    @ManyToMany(mappedBy = "users")
    var roomProfiles: MutableList<RoomProfile>? = mutableListOf(),

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var roommatePreference: RoommatePreference? = null,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var roomPreference: RoomPreference? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
{
    fun toDTO(): UserDTO {
        return UserDTO(
            firstName = firstName,
            lastName = lastName,
            email = email,
            dateOfBirth = dateOfBirth,
            age = ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now()),
            gender = gender,
            lookingFor = lookingFor,
            status = status,
            isOnline = isOnline,
            matchIds = matches?.mapNotNull { it.id }?.toSet() ?: emptySet(),
            userProfile = userProfile?.toDTO(),
            roomProfiles = roomProfiles?.map { it.toDTO() },
            id = id
        )
    }

    fun toUserRequest(): UserRequest {
        return UserRequest(
            firstName = firstName,
            lastName = lastName,
            email = email,
            gender = gender,
            lookingFor = lookingFor,
            dateOfBirth = dateOfBirth,
            id = id
        )
    }

    fun toSummaryDTO(): UserSummaryDTO {
        return UserSummaryDTO(
            firstName = firstName,
            lastName = lastName,
            age = ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now()),
            gender = gender,
            lookingFor = lookingFor,
            isOnline = isOnline,
            id = id,
        )
    }
}

data class UserDTO(
    @NotEmpty val firstName: String,
    @NotEmpty val lastName: String,
    @NotEmpty @Email val email: String,
    val dateOfBirth: LocalDate,
    val age: Long? = null,
    @NotNull val gender: Gender,
    val lookingFor: LookingFor? = LookingFor.OTHER_USER_PROFILES_OR_ROOM_PROFILES,
    val status: UserStatus? = UserStatus.ACTIVE,
    val isOnline: Boolean? = false,
    val matchIds: Set<Long>? = null,
    val userProfile: UserProfileDTO? = null,
    val roomProfiles: List<RoomProfileDTO>? = listOf(),
    val id: Long? = null
)

data class UserRequest(
    @NotEmpty val firstName: String,
    @NotEmpty val lastName: String,
    @NotEmpty @Email val email: String,
    @NotNull val gender: Gender,
    val lookingFor: LookingFor? = LookingFor.OTHER_USER_PROFILES_OR_ROOM_PROFILES,
    val dateOfBirth: LocalDate,
    val id: Long? = null
)

data class UserSummaryDTO(
    val firstName: String,
    val lastName: String,
    val age: Long? = null,
    val gender: Gender,
    val lookingFor: LookingFor?,
    val isOnline: Boolean? = false,
    val id: Long? = null,
)

data class UserPreferenceDTO(
    val roomPreference: RoomPreferenceDTO?,
    val roommatePreference: RoommatePreferenceDTO?,
    val id: Long? = null
)

data class UserPreferenceRequest(
    val roomPreference: RoomPreferenceDTO?,
    val roommatePreference: RoommatePreferenceDTO?
)

data class UserDataDTO(
    // User information
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val lookingFor: LookingFor?,
    val status: UserStatus? = UserStatus.ACTIVE,
    val isOnline: Boolean? = false,
    // Metadata
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
    // Profile(s)
    val userProfile: UserProfileDataDTO? = null,
    val roomProfiles: List<RoomProfileDataDTO?> = listOf(),
    // Preferences and settings
    val roommatePreference: RoommatePreferenceDataDTO? = null,
    val roomPreference: RoomPreferenceDataDTO? = null,
)