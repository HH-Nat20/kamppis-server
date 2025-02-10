package nat20.kamppisserver

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.api.SwipeController
import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.SwipeRequest
import nat20.kamppisserver.domain.SwipeResponse
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.SwipeService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(SwipeController::class)
class SwipeControllerTests @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    @MockkBean
    private lateinit var swipeService: SwipeService
    @MockkBean
    private lateinit var userRepository: UserRepository

    @Test
    fun `should create a swipe successfully`() {
        val user1 = User(email = "alice@example.com", id = 1L)
        val user2 = User(email = "bob@example.com", id = 2L)

        val swipeRequest = SwipeRequest(
            swipingUserId = 1,
            swipedUserId = 2,
            isRightSwipe = true
        )

        val swipeResponse = SwipeResponse(
            swipeId = 1,
            swipingUser = user1,
            swipedUser = user2,
            isRightSwipe = true,
            isMatch = false
        )

        every { userRepository.findByIdOrNull(1L)} returns user1
        every { userRepository.findByIdOrNull(2L)} returns user2
        every { swipeService.swipe(any(),any(), any()) } returns swipeResponse

        mockMvc.post("/api/swipe") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(swipeRequest)
        }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.swipeId") { value(1) }
                jsonPath("$.swipingUser.email") { value("alice@example.com") }
                jsonPath("$.swipedUser.email") { value("bob@example.com") }
                jsonPath("$.isRightSwipe") { value(true) }
            }
    }

}