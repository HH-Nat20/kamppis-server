package nat20.kamppisserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import nat20.kamppisserver.domain.enums.UserStatus

/**
 * Entity class for User.
 */
@Entity
@Table(name = "users")
class User(
    var email: String,

    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.OFFLINE,

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