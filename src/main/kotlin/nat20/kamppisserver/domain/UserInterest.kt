package nat20.kamppisserver.domain

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
    var userProfile: UserProfile,

    @ManyToOne
    @JoinColumn(name = "interest_id", unique = true)
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
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "interest_id")
    var userInterests: MutableList<UserInterest> = mutableListOf(),

    var name: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)