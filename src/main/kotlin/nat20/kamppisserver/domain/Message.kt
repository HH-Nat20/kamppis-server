package nat20.kamppisserver.domain

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * Entity class for Message.
 * @ManyToOne relationship to sender (User) and receiver (User).
 * @ManyToOne relationship to Match.
 */
@Entity
@Table
class Message(
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    var sender: User,

//    @ManyToOne
//    @JoinColumn(name = "receiver_id", nullable = false)
//    var receiver: User,

//    @ManyToOne
//    @JoinColumn(name = "match_id", nullable = false)
//    var match: Match,

    @Column(nullable = false)
    var content: String,

//    @Enumerated(EnumType.STRING)
//    var status: MessageStatus = MessageStatus.CREATED,
//
//    var createdAt: LocalDateTime = LocalDateTime.now(),
//
//    @UpdateTimestamp
//    var updatedAt: LocalDateTime? = null,
//
//    @Column(name = "deleted_at")
//    @UpdateTimestamp
//    var deletedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    )