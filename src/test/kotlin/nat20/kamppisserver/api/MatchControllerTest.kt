package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.*
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.service.MatchService
import org.hamcrest.CoreMatchers.hasItem
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test
import org.springframework.http.MediaType

@WebMvcTest(MatchController::class)
@Import(SecurityConfig::class, JwtUtils::class)
class MatchControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
) {

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @MockkBean
    private lateinit var service: MatchService

    @MockkBean
    private lateinit var repository: UserRepository

    lateinit var jwt: String
    lateinit var user1: UserSummaryDTO
    lateinit var user2: UserSummaryDTO
    lateinit var sampleMatch: MatchDTO
    lateinit var sampleUserProfile: UserProfileDTO

    @BeforeEach
    fun setup() {
        jwt = jwtUtils.generateJwtToken("test@example.com")

        user1 = User(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            gender = Gender.MALE,
            id = 100L
        ).toSummaryDTO()

        user2 = User(
            firstName = "Jane",
            lastName = "Doe",
            email = "jane.doe@example.com",
            dateOfBirth = LocalDate.of(1990, 1, 1),
            gender = Gender.FEMALE,
            id = 200L
        ).toSummaryDTO()

        sampleMatch = MatchDTO(
            id = 1L,
            userIds = setOf(100L, 200L),
            users = setOf(user1, user2),
            createdAt = LocalDateTime.now()
        )

        sampleUserProfile = UserProfileDTO(
            userId = 100L,
            bio = "Hello!"
        )
    }

    @Test
    fun `get all matches when no userId is provided`() {
        val sampleMatches = listOf(sampleMatch)
        every { service.findAll() } returns sampleMatches

        mockMvc.perform(get("/api/matches")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
    }

    @Test
    fun `get matches for user when valid userId is provided`() {
        val sampleMatches = listOf(sampleMatch)
        every { repository.findByIdAndStatus(100L, UserStatus.ACTIVE) } returns mockk()
        every { service.findAllForUser(100L) } returns sampleMatches

        mockMvc.perform(get("/api/matches").param("userId", "100")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].userIds", hasItem(100)))
            .andExpect(jsonPath("$[0].userIds", hasItem(200)))
    }

    @Test
    fun `get matched profiles for user`() {
        val sampleUserProfiles = listOf(sampleUserProfile)
        every { service.findUserProfilesThatMatchWithUser(100L) } returns sampleUserProfiles

        mockMvc.perform(get("/api/matches/profiles/100")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].userId").value(100))
    }

    @Test
    fun `get match by id`() {
        every { service.findOne(1L) } returns sampleMatch

        mockMvc.perform(get("/api/matches/1")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
    }

    @Test
    fun `create new match`() {
        val request = MatchRequest(userIds = setOf(100L, 200L))
        every { service.createMatch(request) } returns sampleMatch

        mockMvc.perform(
            post("/api/matches")
                .header("Authorization", "Bearer $jwt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "userIds": [100, 200]
                    }
                    """.trimIndent()
                )
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
    }
}