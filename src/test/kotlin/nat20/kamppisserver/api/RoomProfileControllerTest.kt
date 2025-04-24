package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.RoomProfileService
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
import java.time.LocalDate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

@WebMvcTest(RoomProfileController::class)
@Import(SecurityConfig::class)
class RoomProfileControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @MockkBean
    private lateinit var service: RoomProfileService

    @MockkBean
    private lateinit var queryService: QueryService

    lateinit var jwt: String
    lateinit var user: User
    lateinit var flatDTO: FlatDTO
    lateinit var roomProfileDTO: RoomProfileDTO
    lateinit var request: RoomProfileRequest

    @BeforeEach
    fun setup() {
        jwt = JwtUtils.generateJwtToken("test@example.com")

        user = User(firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            gender = Gender.MALE,
            id = 1L
        )

        flatDTO = FlatDTO(
            name = "Nice place",
            description = "Sunny",
            location = City.HELSINKI,
            totalRoommates = 3,
            id = 1L
        )

        roomProfileDTO = RoomProfileDTO(
            userIds = listOf(user.id!!),
            flat = flatDTO,
            totalRoommates = 3,
            location = City.HELSINKI,
            rent = 500,
            isPrivateRoom = false,
            furnished = false,
            bio = "A cool place",
            id = 1L
        )

        request = RoomProfileRequest(
            userIds = listOf(user.id!!),
            flatId = flatDTO.id!!,
            rent = 400,
            isPrivateRoom = false,
            furnished = false,
            bio = "Chill area",
            id = 1L
        )
    }

    @Test
    fun `GET all room profiles returns 200 and list`() {
        every { service.findAll() } returns listOf(roomProfileDTO)

        mockMvc.perform(get("/api/room-profiles")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].bio").value("A cool place"))
    }

    @Test
    fun `GET room profile by id returns 200`() {
        every { service.findById(1L) } returns roomProfileDTO

        mockMvc.perform(get("/api/room-profiles/1")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
    }

    @Test
    fun `POST new room profile returns 201`() {
        val created = roomProfileDTO.copy(bio = "Chill area")

        every { service.add(request) } returns created

        mockMvc.perform(
            post("/api/room-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.bio").value("Chill area"))
    }

    @Test
    fun `PUT update room profile returns 200`() {
        val id = request.id
        val updated = roomProfileDTO.copy(bio = "I lied. Area is not chill at all")

        every { service.update(request, id!!) } returns updated

        mockMvc.perform(
            put("/api/room-profiles/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.bio").value("I lied. Area is not chill at all"))
    }

    @Test
    fun `DELETE room profile returns 204`() {
        every { service.delete(roomProfileDTO.id!!) } just Runs

        mockMvc.perform(delete("/api/room-profiles/${roomProfileDTO.id}")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `GET query returns 204 when empty`() {
        val emptyPage: Page<RoomProfileDTO> = Page.empty()
        every { queryService.findRoomProfilesThatMeetCriteria(PageRequest.of(0, 5), 1L) } returns emptyPage

        mockMvc.perform(get("/api/room-profiles/1/query?size=5")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `GET swipersquery returns 204 when empty`() {
        every { queryService.findUserProfilesThatHaveSwipedRoomProfile(PageRequest.of(0, 5), 1L) } returns Page.empty()

        mockMvc.perform(get("/api/room-profiles/1/swipersquery?size=5")
            .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isNoContent)
    }
}