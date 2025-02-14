package nat20.kamppisserver.domain

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * Entity class for Chat.
 * @ManyToOne relationship to User.
 * @ManyToOne relationship to Match.
 * @OneToMany relationship to Message.
 */
@Entity
@Table(name = "chats")
class Chat (
    @ManyToOne
    @JoinColumn(name = "user_id")
    var sender: User,

    @ManyToOne
    @JoinColumn(name = "match_id")
    var receiver: Match,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "message_id")
    var messages: MutableList<Message>? = mutableListOf(),

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