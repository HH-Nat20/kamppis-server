package nat20.kamppisserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

/**
 * Entity class for UserInterest.
 * @ManyToOne relationship to UserProfile and Interest.
 */
@Entity
@Table(name = "user_interests")
class UserInterest(
    @ManyToOne
    @JoinColumn(name = "user_profile_id", unique = true)
    @JsonIgnore
    var userProfile: UserProfile,

    @ManyToOne
    @JoinColumn(name = "interest_id", unique = true)
    @JsonIgnore
    var interest: Interest,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)

/**
 * Entity class for Interest.
 * @OneToMany relationship to UserInterest.
 */
@Entity
@Table
class Interest(
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "interest", orphanRemoval = true)
    var userInterests: MutableList<UserInterest> = mutableListOf(),

    var name: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)