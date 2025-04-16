package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.service.FeedbackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import io.mockk.every
import nat20.kamppisserver.domain.FeedbackDTO
import nat20.kamppisserver.security.JwtUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.junit.jupiter.api.Test

@WebMvcTest(FeedbackController::class)
@Import(SecurityConfig::class)
class FeedbackControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @MockkBean
    private lateinit var service: FeedbackService

    @Test
    fun `findAll should return list of feedbacks`() {
        val feedback1 = FeedbackDTO(feedback = "What is love?", id = 1L)
        val feedback2 = FeedbackDTO(feedback = "Baby don't hurt me", id = 2L)

        every { service.findAll() } returns listOf(feedback1, feedback2)

        val jwt = JwtUtils.generateJwtToken("test@example.com")

        mockMvc.get("/api/feedback") {
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.[0].feedback") { value(feedback1.feedback) }
                jsonPath("$.[1].feedback") { value(feedback2.feedback) }
            }
    }

    @Test
    fun `add should return created feedback`() {
        val request = FeedbackDTO(feedback = "No more", id = null)
        val saved = request.copy(id = 3)

        every { service.add(request) } returns saved

        val jwt = JwtUtils.generateJwtToken("test@example.com")

        mockMvc.post("/api/feedback") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
            header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(3) }
                jsonPath("$.feedback") { value(request.feedback) }
            }
    }
}