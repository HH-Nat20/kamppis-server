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
    @NotEmpty(message = "First name cannot be empty.")
    var firstName: String,

    @NotEmpty(message = "Last name cannot be empty.")
    var lastName: String,

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Invalid email format.")
    var email: String,

    @Past(message = "Date of birth cannot be in the future.")
    var dateOfBirth: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Gender cannot be null.")
    var gender: Gender,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus = UserStatus.ACTIVE,

    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @PastOrPresent(message = "Update date cannot be in the future.")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    @PastOrPresent(message = "Deletion date cannot be in the future.")
    var deletedAt: LocalDateTime? = null,

    // Specifically for use in chat
    @Column(name = "is_online")
    var isOnline: Boolean = false,

    @JsonIgnore
    @ManyToMany(mappedBy = "users") // This makes it bidirectional
    var matches: MutableSet<Match> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)

fun toUserDTO(user: User): UserDTO {
    val userDTO = UserDTO(
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        age = ChronoUnit.YEARS.between(user.dateOfBirth, LocalDate.now()),
        gender = user.gender,
        status = user.status,
        isOnline = user.isOnline,
        matchIds = user.matches.mapNotNull { it.id }.toSet(),
        createdAt = user.createdAt,
        updatedAt = user.updatedAt,
        deletedAt = user.deletedAt,
        //TODO: flatPreferenceDTO = user.flatPreference.toFlatPreferenceDTO()
        //TODO: profileDTO = user.profile.toProfileDTO()
        //TODO: roommatePreferenceDTO = user.roommatePreference.toRoommatePreferenceDTO()
        id = user.id
    )

    return userDTO
}

data class UserDTO(
    @NotEmpty val firstName: String,
    @NotEmpty val lastName: String,
    @NotEmpty @Email val email: String,
    val age: Long? = null,
    @NotNull val gender: Gender,
    val status: UserStatus,
    val isOnline: Boolean,
    val matchIds: Set<Long>,
    @PastOrPresent val createdAt: LocalDateTime,
    @PastOrPresent val updatedAt: LocalDateTime? = null,
    @PastOrPresent val deletedAt: LocalDateTime? = null,
    //TODO: val flatPreferenceDTO: FlatPreferenceDTO,
    //TODO: val profileDTO: ProfileDTO,
    //TODO: val roommatePreferenceDTO: RoommatePreferenceDTO,
    val id: Long? = null
)