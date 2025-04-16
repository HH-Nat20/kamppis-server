package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.service.ProfileService
import nat20.kamppisserver.service.RoomProfileService
import nat20.kamppisserver.service.UserProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.put

@WebMvcTest(ProfileController::class)
@Import(SecurityConfig::class)
class ProfileControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){

    @MockkBean
    private lateinit var profileService: ProfileService

    @MockkBean
    private lateinit var roomProfileRepository: RoomProfileRepository

    @MockkBean
    private lateinit var userProfileRepository: UserProfileRepository

    @MockkBean
    private lateinit var roomProfileService: RoomProfileService

    @MockkBean
    private lateinit var userProfileService: UserProfileService

    @Test
    fun `should update UserProfile successfully`() {
        val userId = 1L
        val userProfileId = 1L
        val userProfileDTO = UserProfileDTO(
            userId = userId,
            bio = "Write bio here",
            id = userProfileId
        )
        val userProfile = mockk<UserProfile>(relaxed = true)

        val jwt = JwtUtils.generateJwtToken("test@example.com")

        every { userProfileRepository.findByIdActive(userProfileId) } returns userProfile
        every { roomProfileRepository.findByIdActive(userProfileId) } returns null
        every { userProfileService.update(match { it.id == userProfileId }, userProfileId) } returns userProfileDTO

        mockMvc.put("/api/profiles/$userProfileId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(userProfileDTO)
            header("Authorization", "Bearer $jwt")
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(userProfileId) }
            }

        verify { userProfileService.update(any(), userProfileId) }
    }

    @Test
    fun `should update RoomProfile successfully`() {
        val roomProfileId = 2L
        val roomProfileDTO = RoomProfileDTO(
            userIds = listOf(1L, 2L),
            flat = FlatDTO(
                name = "Test flat",
                description = "Test",
                location = City.HELSINKI,
                totalRoommates = 2,
                petHousehold = true,
                flatUtilities = mutableListOf(Utilities.WIFI),
                id = 10L
            ),
            totalRoommates = 2,
            location = City.HELSINKI,
            rent = 500,
            isPrivateRoom = true,
            furnished = true,
            furnishedInfo = "Wardrobe, bed",
            bio = "Write bio here",
            id = roomProfileId
        )
        val roomProfile = mockk<RoomProfile>(relaxed = true)

        val jwt = JwtUtils.generateJwtToken("test@example.com")

        every { roomProfileRepository.findByIdActive(roomProfileId) } returns roomProfile
        every { userProfileRepository.findByIdActive(roomProfileId) } returns null
        every { roomProfileService.update(match { it.id == roomProfileId }, roomProfileId) } returns roomProfileDTO

        mockMvc.put("/api/profiles/$roomProfileId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(roomProfileDTO)
            header("Authorization", "Bearer $jwt")
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(roomProfileId) }
            }

        verify { roomProfileService.update(any(), roomProfileId) }
    }

    @Test
    fun `should return 404 when profile not found`() {
        val userId = 3L
        val profileId = 3L
        val userProfileDTO = UserProfileDTO(
            userId = userId,
            bio = "Nonexistent user",
            id = profileId
        )

        val jwt = JwtUtils.generateJwtToken("test@example.com")

        every { userProfileRepository.findByIdActive(profileId) } returns null
        every { roomProfileRepository.findByIdActive(profileId) } returns null

        mockMvc.put("/api/profiles/$profileId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(userProfileDTO)
            header("Authorization", "Bearer $jwt")
        }
            .andExpect {
                status { isNotFound() }
            }
    }
}