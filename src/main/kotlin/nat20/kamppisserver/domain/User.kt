package nat20.kamppisserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PastOrPresent
import nat20.kamppisserver.domain.enums.UserStatus
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Invalid email format.")
    var email: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus = UserStatus.ACTIVE,

    @Column(name = "deleted_at")
    @PastOrPresent(message = "Deletion date cannot be in the future.")
    var deletedAt: LocalDateTime? = null,

    // Specifically for use in chat
    @Column(name = "is_online")
    var isOnline: Boolean = false,

    /*
    * ADD FIELDS HERE AS REQUIRED
    * */
    @JsonIgnore
    @ManyToMany(mappedBy = "users") // This makes it bidirectional
    var matches: MutableSet<Match> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun toUserDTO(user: User): UserDTO {
        val userDTO = UserDTO(
            email = user.email,
            status = user.status,
            isOnline = user.isOnline,
            matchIds = user.matches.mapNotNull { it.id }.toSet(),
            id = user.id
        )
        return userDTO
    }
}

data class UserDTO(
    @NotEmpty @Email val email: String,
    val status: UserStatus,
    val isOnline: Boolean,
    val matchIds: Set<Long>,
    val id: Long? = null
)