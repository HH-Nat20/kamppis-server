package nat20.kamppisserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import nat20.kamppisserver.domain.enums.UserStatus
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * Entity class for User.
 */
@Entity
@Table(name = "users")
class User(
    var email: String,

    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.ACTIVE,

    @Column(name = "deleted_at")
    @UpdateTimestamp
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
)