package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.QueryService
import nat20.kamppisserver.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.Test

@WebMvcTest(UserController::class)
class UserControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var userRepository: UserRepository

    @MockkBean
    lateinit var queryService: QueryService

    @MockkBean
    lateinit var userService: UserService

    @Test
    fun `List all users`() {
        val bobJohnson = User(
            email = "bob.johnson@example.com",
        )

        val charlieDavis = User(
            email = "charlie.davis@example.com",
        )

        every { userRepository.findAllByStatus(UserStatus.ACTIVE) } returns mutableListOf(bobJohnson, charlieDavis)
        mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].email").value(bobJohnson.email))
            .andExpect(jsonPath("\$.[1].email").value(charlieDavis.email))
    }

    @Test
    fun `Find user by id`() {
        val bobJohnson = User(
            email = "bob.johnson@example.com",
        )
        every { userRepository.findByIdAndStatus(any(), UserStatus.ACTIVE) } returns bobJohnson
        mockMvc.perform(get("/api/users/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.email").value(bobJohnson.email))
    }
}