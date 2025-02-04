package nat20.kamppisserver.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
class User(
    var email: String,
    var firstName: String,
    var lastName: String,

    // user is not required to reveal their age
    var dateOfBirth: LocalDate? = null,

    // .
    // .  ADD FIELDS HERE AS REQUIRED
    // .

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    )