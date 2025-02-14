package nat20.kamppisserver.domain

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * Entity class for Message.
 * @ManyToOne relationship to User.
 * @ManyToOne relationship to Chat.
 */
@Entity
@Table(name = "messages")
class Message(
    @ManyToOne
    @JoinColumn(name = "user_id")
    var sender: User,

    @ManyToOne
    @JoinColumn(name = "match_id")
    var receiver: Match,

    @Column(nullable = false)
    var content: String,

    @Enumerated(EnumType.STRING)
    var status: MessageStatus = MessageStatus.CREATED,

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