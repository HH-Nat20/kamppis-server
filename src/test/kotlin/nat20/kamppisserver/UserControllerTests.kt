package nat20.kamppisserver

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.api.UserController
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.QueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import kotlin.test.Test

@WebMvcTest(UserController::class)
class UserControllerTests(@Autowired private val mockMvc: MockMvc) {

    @MockkBean
    lateinit var userRepository: UserRepository

    @MockkBean
    lateinit var queryService: QueryService

    @Test
    fun `List all users`() {
        val bobJohnson = User(
            email = "bob.johnson@example.com",
        )

        val charlieDavis = User(
            email = "charlie.davis@example.com",
        )

        every { userRepository.findAll() } returns listOf(bobJohnson, charlieDavis)
        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
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
        every { userRepository.findByIdOrNull(any()) } returns bobJohnson
        mockMvc.perform(get("/api/user/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.email").value(bobJohnson.email))
    }
}