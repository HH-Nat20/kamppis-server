package nat20.kamppisserver.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "swipes")
class Swipe(
    @ManyToOne var swipingUser: User,
    @ManyToOne var swipedUser: User,
    var isRightSwipe: Boolean,
    var createdAt: LocalDateTime,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null)