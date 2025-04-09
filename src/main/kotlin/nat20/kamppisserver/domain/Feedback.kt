package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDateTime

@Entity
@Table(name = "feedbacks")
class Feedback(

    @Column(columnDefinition = "TEXT")
    var feedback: String = "Write your feedback here",

    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun toDTO(): FeedbackDTO {
        return FeedbackDTO(
            feedback = feedback,
            id = id
        )
    }
}

data class FeedbackDTO(
    val feedback: String,
    val id: Long?
)