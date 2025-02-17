package nat20.kamppisserver.domain

import jakarta.persistence.*

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)