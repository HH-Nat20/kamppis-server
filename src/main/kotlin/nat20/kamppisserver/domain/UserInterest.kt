package nat20.kamppisserver.domain

import jakarta.persistence.*

@Entity
@Table(name = "user_interests")
class UserInterest(
    @ManyToOne
    @JoinColumn(name = "user_id", unique = true)
    var user: User,

    @ManyToOne
    @JoinColumn(name = "interest_id", unique = true)
    var interest: Interest,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)

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