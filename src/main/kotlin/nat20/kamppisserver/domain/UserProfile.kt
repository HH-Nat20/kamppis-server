package nat20.kamppisserver.domain

import jakarta.persistence.*
import nat20.kamppisserver.util.JsonStringListConverter
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Entity class for UserProfile.
 * @OneToOne relationship to User.
 * @OneToMany relationship to UserHabit.
 * @OneToMany relationship to UserInterest.
 */
@Entity
@Table(name = "user_profiles")
class UserProfile(
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    var user: User,

    var firstName: String,
    var lastName: String,
    var dateOfBirth: LocalDate,
    var gender: Gender,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "userProfile")
    var userPhotos: MutableList<UserPhoto>? = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var userHabits: MutableList<UserHabit>? = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var userInterests: MutableList<UserInterest>? = mutableListOf(),

    var bio: String? = null,
    var minAgePreference: Int? = null,
    var maxAgePreference: Int? = null,
    var preferredGender: Gender = Gender.NOT_IMPORTANT,

    @Convert(converter = JsonStringListConverter::class)
    var locations: List<String> = emptyList(),

    /*
    * ADD FIELDS HERE AS REQUIRED
    * */

    var createdAt: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    @UpdateTimestamp
    var deletedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)