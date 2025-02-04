package nat20.kamppisserver.domain

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
class Match(
    @ManyToMany
    @JoinTable(
        name = "matches_users",
        joinColumns = [JoinColumn(name = "match_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var users: MutableSet<User> = mutableSetOf(),

    var createdAt: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
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
}