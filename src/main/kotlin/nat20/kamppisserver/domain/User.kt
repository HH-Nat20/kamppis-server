package nat20.kamppisserver.domain

import jakarta.persistence.*

/**
 * Entity class for User.
 */
@Entity
@Table(name = "users")
class User(
    var email: String,

    /*
    * ADD FIELDS HERE AS REQUIRED
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)