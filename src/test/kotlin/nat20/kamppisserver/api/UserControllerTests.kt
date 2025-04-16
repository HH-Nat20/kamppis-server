package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.domain.UserDTO
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import kotlin.test.Test

@Import(SecurityConfig::class) // Import your security config
@WebMvcTest(UserController::class)
class UserControllerTests @Autowired constructor(
    val mockMvc: MockMvc
){

/*      Probably needed later
    @MockkBean
    private lateinit var authenticationManager: AuthenticationManager*/

    @MockkBean
    lateinit var userService: UserService

    @Test
    fun `List all users`() {
        val bobJohnson = UserDTO(
            email = "bob.johnson@example.com",
            firstName = "Bob",
            lastName = "Johnson",
            age = 39,
            dateOfBirth = LocalDate.of(1990, 5, 14),
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            isOnline = false,
            matchIds = setOf(1, 2)
        )

        val charlieDavis = UserDTO(
            email = "charlie.davis@example.com",
            firstName = "Charlie",
            lastName = "Davis",
            age = 27,
            dateOfBirth = LocalDate.of(1990, 5, 14),
            gender = Gender.OTHER,
            status = UserStatus.ACTIVE,
            isOnline = false,
            matchIds = setOf(1, 2)
        )

        every { userService.findAllMockUsers() } returns listOf(bobJohnson, charlieDavis)

        val jwt = JwtUtils.generateJwtToken("fake@example.com")

        mockMvc.perform(get("/api/users/mock").accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer $jwt"))
        .andExpect(status().isOk)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].email").value(bobJohnson.email))
            .andExpect(jsonPath("\$.[1].email").value(charlieDavis.email))
    }

    @Test
    fun `Find user by id`() {
        val bobJohnson = UserDTO(
            email = "bob.johnson@example.com",
            firstName = "Bob",
            lastName = "Johnson",
            age = 39,
            dateOfBirth = LocalDate.of(1990, 5, 14),
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            isOnline = false,
            matchIds = setOf(1, 2)
        )

        every { userService.findById(1) } returns bobJohnson

        val jwt = JwtUtils.generateJwtToken("bob.johnson@example.com")

        mockMvc.perform(get("/api/users/1").accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer $jwt"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.email").value(bobJohnson.email))
    }
}