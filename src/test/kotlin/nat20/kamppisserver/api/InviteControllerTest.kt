package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.*
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.service.InviteService
import nat20.kamppisserver.service.RoomProfileService
import nat20.kamppisserver.setup.StandaloneSetup
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import kotlin.test.Test

@WebMvcTest(InviteController::class)
@Import(SecurityConfig::class, JwtUtils::class)
class InviteControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val jwtUtils: JwtUtils
) {

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

    val jwt = jwtUtils.generateJwtToken("test@example.com")

    lateinit var user: User
    lateinit var testInvite: RoomProfileInvite
    lateinit var testInviteResponse: InviteResponse

    @BeforeEach
    fun init() {
        StandaloneSetup.setup()
        user = StandaloneSetup.user1
        testInvite = StandaloneSetup.invite
        testInviteResponse = StandaloneSetup.inviteResponse
    }

    @Test
    fun `generate invite token when not exists`() {
        testInviteResponse.message = "Invite created successfully!"
        every { inviteService.createInviteOrReturnExistingInvite(123L) } returns testInviteResponse

        mockMvc.perform(post("/api/invites/generate-invitetoken/123")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("Invite created successfully!"))
            .andExpect(jsonPath("$.inviteToken").value("INV123"))
    }

    @Test
    fun `generate invite token when one already exists`() {
        testInviteResponse.message = "Invite already exists, use code INV123"
        every { inviteService.createInviteOrReturnExistingInvite(123L) } returns testInviteResponse

        mockMvc.perform(post("/api/invites/generate-invitetoken/123")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.message").value("Invite already exists, use code INV123"))
    }

    @Test
    fun `join room with valid invite`() {
        testInviteResponse.message = "Room joined successfully"
        every { inviteService.joinRoom("INV123", "Bearer $jwt") } returns testInviteResponse

        mockMvc.perform(
            put("/api/invites/join/INV123")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("Room joined successfully"))
    }

    @Test
    fun `join room with expired invite`() {
        testInvite.expiresAt = LocalDateTime.now().minusDays(2)
        val expiredInviteResponse = toInviteResponse(testInvite)
        expiredInviteResponse.message = "Invite code INV123 has expired"
        every { inviteService.joinRoom("INV123", "Bearer $jwt") } returns expiredInviteResponse

        mockMvc.perform(
            put("/api/invites/join/INV123")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isGone)
            .andExpect(jsonPath("$.message").value("Invite code INV123 has expired"))
    }

    @Test
    fun `join room when user already added`() {
        testInviteResponse.message = "User already added to the room"
        every { inviteService.joinRoom("INV123", "Bearer $jwt") } returns testInviteResponse

        mockMvc.perform(
            put("/api/invites/join/INV123")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.message").value("User already added to the room"))
    }
}