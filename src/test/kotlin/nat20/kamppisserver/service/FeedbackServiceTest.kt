package nat20.kamppisserver.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import nat20.kamppisserver.domain.Feedback
import nat20.kamppisserver.domain.FeedbackDTO
import nat20.kamppisserver.repository.FeedbackRepository
import nat20.kamppisserver.setup.StandaloneSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FeedbackServiceTest {

    private lateinit var repository: FeedbackRepository
    private lateinit var service: FeedbackService

    lateinit var feedback: Feedback

    @BeforeEach
    fun setup() {
        repository = mockk()
        service = FeedbackService(repository)

        StandaloneSetup.setup()
        feedback = StandaloneSetup.feedback
    }

    @Test
    fun `findAll should return list of FeedbackDTOs`() {
        val feedbacks = listOf(feedback)

        every { repository.findAll() } returns feedbacks

        val result = service.findAll()

        assertThat(result).hasSize(1)
        assertThat(result[0].feedback).isEqualTo("Ain't this a surprise")
        verify { repository.findAll() }
    }

    @Test
    fun `add should save and return FeedbackDTO`() {
        val request = FeedbackDTO(feedback = "Awesome experience!")
        val savedFeedback = Feedback(id = 123, feedback = request.feedback)

        every { repository.save(any()) } returns savedFeedback

        val result = service.add(request)

        assertThat(result.feedback).isEqualTo("Awesome experience!")
        verify { repository.save(any()) }
    }
}