package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.repository.*
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.service.InviteService
import nat20.kamppisserver.service.RoomProfileService
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

@WebMvcTest(InviteController::class)
@Import(SecurityConfig::class, JwtUtils::class)
class InviteControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
) {

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @MockkBean
    private lateinit var inviteService: InviteService

    @MockkBean
    private lateinit var roomProfileService: RoomProfileService

    @MockkBean
    private lateinit var roomProfileInviteRepository: RoomProfileInviteRepository

    @MockkBean
    private lateinit var userRepository: UserRepository

    @MockkBean
    private lateinit var roomProfileRepository: RoomProfileRepository

    lateinit var jwt: String
    lateinit var user: User
    lateinit var testInvite: RoomProfileInvite

    @BeforeEach
    fun setup() {
        jwt = jwtUtils.generateJwtToken("test@example.com")

        user = User(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            gender = Gender.MALE,
            id = 1L
        )

        testInvite = RoomProfileInvite(
            roomProfileId = 123L,
            roomProfileInviteToken = "INV123",
            expiresAt = LocalDateTime.now().plusDays(1)
        )
    }

    @Test
    fun `generate invite token when not exists`() {
        every { inviteService.findInviteByRoomProfileId(123L) } returns null
        every { inviteService.generateAndSaveRoomInvite(123L) } returns testInvite

        mockMvc.perform(post("/api/invites/generate-invitetoken/123")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("Invite created successfully!"))
            .andExpect(jsonPath("$.inviteToken").value("INV123"))
    }

    @Test
    fun `generate invite token when one already exists`() {
        every { inviteService.findInviteByRoomProfileId(123L) } returns testInvite

        mockMvc.perform(post("/api/invites/generate-invitetoken/123")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.message").value("Invite already exists, use code INV123"))
    }

    @Test
    fun `join room with valid invite`() {
        val roomProfileRequest = mockk<RoomProfileRequest>()
        val roomProfileDTO = mockk<RoomProfileDTO>()

        every { roomProfileInviteRepository.findInviteByInviteToken("INV123") } returns testInvite
        every { userRepository.findByEmail("test@example.com") } returns user
        every { roomProfileService.findUsersRoomProfiles(testInvite.roomProfileId, user.id!!) } returns emptyList()
        every { inviteService.generateRoomProfileRequest(testInvite.roomProfileId, user.id!!) } returns roomProfileRequest
        every { roomProfileService.update(roomProfileRequest, testInvite.roomProfileId) } returns roomProfileDTO

        mockMvc.perform(
            put("/api/invites/join/INV123")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("Room joined successfully"))
    }

    @Test
    fun `join room with expired invite`() {
        testInvite.expiresAt = LocalDateTime.now().minusMinutes(1)
        val expiredInvite = testInvite
        every { roomProfileInviteRepository.findInviteByInviteToken("INV123") } returns expiredInvite

        mockMvc.perform(
            put("/api/invites/join/INV123")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isGone)
            .andExpect(jsonPath("$.message").value("Invite code INV123 has expired"))
    }

    @Test
    fun `join room when user already added`() {
        every { roomProfileInviteRepository.findInviteByInviteToken("INV123") } returns testInvite
        every { userRepository.findByEmail("test@example.com") } returns user
        every { roomProfileService.findUsersRoomProfiles(testInvite.roomProfileId, user.id!!) } returns listOf(mockk())

        mockMvc.perform(
            put("/api/invites/join/INV123")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.message").value("User already added to the room"))
    }
}