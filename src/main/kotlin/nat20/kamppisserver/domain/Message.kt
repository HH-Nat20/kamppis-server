package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDateTime

@Entity
@Table
class Message(
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    @NotNull(message = "Sender cannot be null.")
    var sender: User,

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    @NotNull(message = "Match cannot be null.")
    var match: Match,

    @Column(nullable = false)
    @NotEmpty(message = "Message content cannot be empty.")
    var content: String,

    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun toMessageDTO(): MessageDTO {
        return MessageDTO(
            id = id,
            senderEmail = sender.email,
            senderId = sender.id ?: throw IllegalStateException("Sender ID is null"),
            matchId = match.id ?: throw IllegalStateException("Match ID is null"),
            content = content,
            createdAt = createdAt,
        )
    }
}

data class MessageDTO(
    val id: Long? = null,
    @NotNull val senderEmail: String,
    @NotNull val senderId: Long,
    @NotNull val matchId: Long,
    @NotEmpty val content: String,
    @PastOrPresent val createdAt: LocalDateTime?,
)