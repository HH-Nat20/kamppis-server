package nat20.kamppisserver.service

import jakarta.validation.Valid
import nat20.kamppisserver.domain.Feedback
import nat20.kamppisserver.domain.FeedbackDTO
import nat20.kamppisserver.repository.FeedbackRepository
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class FeedbackService(private val repository: FeedbackRepository) {

    fun findAll(): List<FeedbackDTO> {
        return repository.findAll().map { it.toDTO() }
    }

    fun add(@Valid request: FeedbackDTO): FeedbackDTO {
        val feedback = Feedback(
            feedback = request.feedback
        )

        val addedFeedback = repository.save(feedback)
        return addedFeedback.toDTO()
    }
}