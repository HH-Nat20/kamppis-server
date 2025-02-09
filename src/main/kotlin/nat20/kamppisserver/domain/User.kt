package nat20.kamppisserver.domain

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Entity class for User.
 * @OneToMany relationship to UserHabit and UserInterest.
 */
@Entity
@Table(name = "users")
class User(
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id")
    var userHabits: MutableList<UserHabit> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id")
    var userInterests: MutableList<UserInterest> = mutableListOf(),

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