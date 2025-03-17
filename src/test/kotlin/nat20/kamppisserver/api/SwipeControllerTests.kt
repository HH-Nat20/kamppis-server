package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.domain.SwipeRequest
import nat20.kamppisserver.domain.SwipeResponse
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.SwipeService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@WebMvcTest(SwipeController::class)
@Import(SecurityConfig::class) // Import your security config
class SwipeControllerTests @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    @MockkBean
    private lateinit var swipeService: SwipeService
    @MockkBean
    private lateinit var userRepository: UserRepository

/*    Probably needed later
    @MockkBean
    private lateinit var authenticationManager: AuthenticationManager*/

    @Test
    fun `should create a swipe successfully`() {
        val user1 = User(
            email = "alice.smith@example.com",
            firstName = "Alice",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1990, 5, 14), // Age 34
            gender = Gender.FEMALE,
            id = 1L)
        val user2 = User(
            email = "bob.johnson@example.com",
            firstName = "Bob",
            lastName = "Johnson",
            dateOfBirth = LocalDate.of(1985, 11, 22), // Age 39
            gender = Gender.MALE,
            id = 2L)

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

        every { userRepository.findByIdAndStatus(1L, UserStatus.ACTIVE)} returns user1
        every { userRepository.findByIdAndStatus(2L, UserStatus.ACTIVE)} returns user2
        every { swipeService.swipe(any(),any(), any()) } returns swipeResponse

        mockMvc.post("/api/swipes") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(swipeRequest)
        }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.swipeId") { value(1) }
                jsonPath("$.swipingUser.email") { value("alice.smith@example.com") }
                jsonPath("$.swipedUser.email") { value("bob.johnson@example.com") }
                jsonPath("$.isRightSwipe") { value(true) }
            }
    }

}