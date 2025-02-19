package nat20.kamppisserver.domain

import jakarta.persistence.*

@Entity
@Table
data class Chat(
    val messageId: Long,
    val senderId: Long,
    val receiverId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)