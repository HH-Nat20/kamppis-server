package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PastOrPresent
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
class Match(
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "matches_users",
        joinColumns = [JoinColumn(name = "match_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )

    @NotEmpty(message = "A match must have at least one user.")
    var users: MutableSet<User> = mutableSetOf(),

    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @PastOrPresent(message = "Update date cannot be in the future.")
    var updatedAt: LocalDateTime? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

) {
    fun addUser(user: User) {
        users.add(user)
        updatedAt = LocalDateTime.now()
    }

    fun removeUser(user: User) {
        users.remove(user)
        updatedAt = LocalDateTime.now()
    }

    fun toDTO(): MatchDTO {
        return MatchDTO(
            userIds = users.mapNotNull { it.id }.toSet(),
            id = id
        )
    }
}

data class MatchRequest(
    @NotEmpty val userIds: Set<Long>
)

data class MatchDTO(
    @NotEmpty val userIds: Set<Long>,
    val id: Long? = null,
)