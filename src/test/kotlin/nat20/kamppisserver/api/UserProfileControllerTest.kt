package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.UserProfileService
import nat20.kamppisserver.setup.StandaloneSetup
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

@WebMvcTest(UserProfileController::class)
@Import(SecurityConfig::class, JwtUtils::class)
class UserProfileControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val jwtUtils: JwtUtils
) {

    @MockkBean
    private lateinit var service: UserProfileService

    @MockkBean
    private lateinit var queryService: QueryService

    val jwt = jwtUtils.generateJwtToken("test@example.com")

    lateinit var user: UserDTO
    lateinit var userProfileDTO: UserProfileDTO
    lateinit var userProfileRequest: UserProfileRequest

    @BeforeEach
    fun init() {
        StandaloneSetup.setup()
        user = StandaloneSetup.user1.toDTO()
        userProfileDTO = StandaloneSetup.userProfile.toDTO()
        userProfileRequest = StandaloneSetup.userProfileRequest
    }

    @Test
    fun `GET all user profiles returns 200`() {
        every { service.findAll() } returns listOf(userProfileDTO)

        mockMvc.perform(get("/api/user-profiles")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].bio").value("Test bio"))
    }

    @Test
    fun `GET user profile by ID returns 200`() {
        every { service.findById(1L) } returns userProfileDTO

        mockMvc.perform(get("/api/user-profiles/1")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.bio").value("Test bio"))
    }

    @Test
    fun `POST add user profile returns 201`() {
        every { service.add(userProfileRequest) } returns userProfileDTO

        mockMvc.perform(
            post("/api/user-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileRequest))
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
    }

    @Test
    fun `PUT update user profile returns 200`() {
        every { service.update(userProfileRequest, 1L) } returns userProfileDTO

        mockMvc.perform(
            put("/api/user-profiles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileRequest))
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
    }

    @Test
    fun `GET user profiles query returns 204 when no results exist`() {
        val emptyPage = Page.empty<UserProfileDTO>()
        every { queryService.findUserProfilesThatMeetCriteria(PageRequest.of(0, 5), 1L) } returns emptyPage

        mockMvc.perform(get("/api/user-profiles/1/query?size=5")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isNoContent)
    }
}