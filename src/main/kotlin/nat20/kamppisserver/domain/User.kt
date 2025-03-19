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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus = UserStatus.ACTIVE,

    // Specifically for use in chat
    @Column(name = "is_online")
    var isOnline: Boolean = false,

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
    var matches: MutableSet<Match> = mutableSetOf(),

    @OneToOne
    var userProfile: UserProfile? = null,

    @ManyToMany(mappedBy = "users")
    var roomProfiles: MutableList<RoomProfile>? = mutableListOf(),

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
            age = ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now()),
            gender = gender,
            status = status,
            isOnline = isOnline,
            matchIds = matches.mapNotNull { it.id }.toSet(),
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
            //TODO: flatPreferenceDTO = user.roomPreference.toRoomPreferenceDTO()
            userProfile = userProfile?.toDTO(),
            roomProfiles = roomProfiles?.map { it.toDTO() },
            //TODO: roommatePreferenceDTO = user.roommatePreference.toRoommatePreferenceDTO()
            id = id
        )
    }
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
    //TODO: val roomPreferenceDTO: RoomPreferenceDTO,
    val userProfile: UserProfileDTO? = null,
    val roomProfiles: List<RoomProfileDTO>? = listOf(),
    //TODO: val roommatePreferenceDTO: RoommatePreferenceDTO,
    val id: Long? = null
)