package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.*
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.service.MatchService
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.hasItems
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test
import org.springframework.http.MediaType

@WebMvcTest(MatchController::class)
@Import(SecurityConfig::class, JwtUtils::class)
class MatchControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val jwtUtils: JwtUtils
) {

    @MockkBean
    private lateinit var service: MatchService

    @MockkBean
    private lateinit var repository: UserRepository

    val jwt = jwtUtils.generateJwtToken("test@example.com")

    lateinit var user1: UserSummaryDTO
    lateinit var user2: UserSummaryDTO
    lateinit var sampleMatch: MatchDTO
    lateinit var sampleUserProfile: UserProfileDTO

    @BeforeEach
    fun init() {
        StandaloneSetup.setup()
        user1 = StandaloneSetup.user1.toSummaryDTO()
        user2 = StandaloneSetup.user2.toSummaryDTO()
        sampleMatch = StandaloneSetup.match.toDTO()
        sampleUserProfile = StandaloneSetup.userProfile.toDTO()
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
        every { repository.findByIdAndStatus(user1.id!!, UserStatus.ACTIVE) } returns mockk()
        every { service.findAllForUser(user1.id!!) } returns sampleMatches

        mockMvc.perform(get("/api/matches").param("userId", "${user1.id}")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].userIds", hasItems(user1.id?.toInt(), user2.id?.toInt())))
    }

    @Test
    fun `get matched profiles for user`() {
        val sampleUserProfiles = listOf(sampleUserProfile)
        every { service.findUserProfilesThatMatchWithUser(user1.id!!) } returns sampleUserProfiles

        mockMvc.perform(get("/api/matches/profiles/${user1.id}")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].userId").value(user1.id))
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
        val request = MatchRequest(userIds = setOf(user1.id!!, user2.id!!))
        every { service.createMatch(request) } returns sampleMatch

        mockMvc.perform(
            post("/api/matches")
                .header("Authorization", "Bearer $jwt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "userIds": [${user1.id}, ${user2.id}]
                    }
                    """.trimIndent()
                )
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
    }
}