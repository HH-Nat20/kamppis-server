package nat20.kamppisserver.domain

import jakarta.persistence.*

@Entity
@Table(name = "user_habits")
class UserHabit(
    @ManyToOne
    @JoinColumn(name = "user_id", unique = true)
    var user: User,

    @ManyToOne
    @JoinColumn(name = "habit_id", unique = true)
    var habit: Habit,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)

@Entity
@Table
class Habit(
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "habit_id")
    var userHabits: MutableList<UserHabit> = mutableListOf(),

    var name: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)