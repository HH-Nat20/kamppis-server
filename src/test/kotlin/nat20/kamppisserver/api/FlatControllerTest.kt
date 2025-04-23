package nat20.kamppisserver.api

import nat20.kamppisserver.security.SecurityConfig
import org.springframework.context.annotation.Import
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.domain.FlatDTO
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.service.FlatService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.put

@WebMvcTest(FlatController::class)
@Import(SecurityConfig::class)
class FlatControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @MockkBean
    private lateinit var flatService: FlatService

    lateinit var flat1: FlatDTO
    lateinit var flat2: FlatDTO

    val jwt = JwtUtils.generateJwtToken("test@example.com")

    @BeforeEach
    fun setup() {
        flat1 = FlatDTO(
                id = 1,
                name = "Nice place",
                description = "Sunny",
                location = City.HELSINKI,
                totalRoommates = 3,
                petHousehold = false,
                flatUtilities = mutableListOf(Utilities.WIFI)
            )

        flat2 = FlatDTO(
                id = 2,
                name = "Cozy loft",
                description = "Downtown",
                location = City.TAMPERE,
                totalRoommates = 2,
                petHousehold = true,
                flatUtilities = mutableListOf(Utilities.LAUNDRY_MACHINE)
            )
    }

    @Test
    fun `GET all flats returns 200 with list`() {
        val flats = listOf(flat1, flat2)

        every { flatService.findAll() } returns flats

        mockMvc.get("/api/flats") {
            header("Authorization", "Bearer $jwt")
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.size()") { value(flats.size) }
            }
    }

    @Test
    fun `GET flat by id returns 200 with matching flat`() {
        val id = flat1.id
        every { flatService.findById(id!!) } returns flat1

        mockMvc.get("/api/flats/$id") {
            header("Authorization", "Bearer $jwt")
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(id) }
                jsonPath("$.name") { value("Nice place") }
            }
    }

    @Test
    fun `POST flat returns 201 when valid`() {
        val input = flat1
        val saved = input.copy(id = 99L)

        every { flatService.add(input) } returns saved

        mockMvc.post("/api/flats") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(input)
            header("Authorization", "Bearer $jwt")
        }.andExpect {
            status { isCreated() }
            jsonPath("$.id") { value(99) }
        }
    }

    @Test
    fun `PUT flat returns 200 when successful`() {
        val input = flat2
        val id = input.id
        val updated = input.copy(id = id)

        every { flatService.update(input, id!!) } returns updated

        mockMvc.put("/api/flats/$id") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(input)
            header("Authorization", "Bearer $jwt")
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(id) }
        }
    }
}