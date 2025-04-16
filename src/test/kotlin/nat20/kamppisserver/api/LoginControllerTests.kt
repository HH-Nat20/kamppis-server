package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.domain.RoomPreference
import nat20.kamppisserver.domain.UserDTO
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.repository.RoomPreferenceRepository
import nat20.kamppisserver.repository.RoommatePreferenceRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.security.AuthService
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.security.GitHubAuthService
import nat20.kamppisserver.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@WebMvcTest(LoginController::class)
@Import(SecurityConfig::class) // Import your security config
class LoginControllerTests @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    @MockkBean
    private lateinit var userService: UserService

    @MockkBean
    private lateinit var gitHubAuthService: GitHubAuthService

    @MockkBean
    private lateinit var authService: AuthService

    @MockkBean
    private lateinit var userRepository: UserRepository

    @MockkBean
    private lateinit var profileRepository: ProfileRepository

    @MockkBean
    private lateinit var roomPreferenceRepository: RoomPreferenceRepository

    @MockkBean
    private lateinit var roommatePreferenceRepository: RoommatePreferenceRepository

    @AfterEach
    fun tearDown() {
        SecurityContextHolder.clearContext()
    }

    @Test
    fun `login should return JWT for valid user`() {
        val mockUserDTO = UserDTO(
            email = "alice.smith@example.com",
            firstName = "Alice",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1990, 5, 14),
            age = 34,
            gender = Gender.FEMALE,
            status = UserStatus.ACTIVE,
            isOnline = false,
            matchIds = setOf(1, 2)
        )

        every { userService.findActiveUserByEmail(mockUserDTO.email) } returns mockUserDTO

        mockMvc.post("/api/login/mock?email=${mockUserDTO.email}")
            .andExpect {
                status { isOk() }
                jsonPath("$.token") { isNotEmpty() }
            }
    }

    @Test
    fun `should access protected endpoint with valid JWT`() {
        val mockUserDTO = UserDTO(
            email = "alice.smith@example.com",
            firstName = "Alice",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1990, 5, 14),
            age = 34,
            gender = Gender.FEMALE,
            status = UserStatus.ACTIVE,
            isOnline = false,
            matchIds = setOf(1, 2)
        )

        every { userService.findActiveUserByEmail(mockUserDTO.email) } returns mockUserDTO

        // Step 1: Perform login and extract the token
        val token = mockMvc.post("/api/login/mock?email=${mockUserDTO.email}")
            .andExpect {
                status { isOk() }
                jsonPath("$.token") { isNotEmpty() }
            }
            .andReturn()
            .response
            .contentAsString
            .let { responseBody ->
                val jsonNode = objectMapper.readTree(responseBody)
                jsonNode.get("token").asText() // Extract the token from JSON response
            }

        // Step 2: Set authentication in SecurityContext
        val authentication = UsernamePasswordAuthenticationToken(mockUserDTO.email, null, emptyList())
        SecurityContextHolder.getContext().authentication = authentication

        // Step 2: Use the extracted token to access the protected endpoint
        mockMvc.get("/api/login/protected") {
            header("Authorization", "Bearer $token")
        }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should not access protected endpoint without JWT`() {
        mockMvc.get("/api/login/protected")
            .andExpect {
                status { isForbidden() }
            }
    }

    @Test
    fun `should not access protected endpoint with expired JWT`() {
        val email = "alice.smith@example.com"
        val token = JwtUtils.generateExpiredToken(email)

        val result = mockMvc.get("/api/login/protected") {
            header("Authorization", "Bearer $token")
        }
            .andExpect {
                status { isForbidden() }
            }
            .andReturn() // Retrieve the result object

        // Access the error message from MockHttpServletResponse
        val errorMessage = result.response.errorMessage

        // Assert that the error message is as expected
        assert(errorMessage == "Token has expired") {
            "Expected error message to be 'Token has expired', but was $errorMessage"
        }
    }
}