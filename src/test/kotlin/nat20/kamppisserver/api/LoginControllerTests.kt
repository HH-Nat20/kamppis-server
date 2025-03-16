package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.ObjectMapper
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.SecurityConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(LoginController::class)
@Import(SecurityConfig::class) // Import your security config
class LoginControllerTests @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    @Test
    fun `login should return JWT for valid user`() {
        val email = "alice.smith@example.com"

        mockMvc.post("/api/login?email=$email")
        .andExpect {
            status { isOk() }
            jsonPath("$.token") { isNotEmpty() }
        }
    }

    @Test
    fun `login should return 400 for invalid user`() {
        val email = "invalid@example.com"

        mockMvc.post("/api/login?email=$email")
        .andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should access protected endpoint with valid JWT`() {
        val email = "alice.smith@example.com"

        // Step 1: Perform login and extract the token
        val token = mockMvc.post("/api/login?email=$email")
            .andExpect {
                status { isOk() }
                jsonPath("$.token") { isNotEmpty() }
            }
            .andReturn()
            .response
            .contentAsString
            .let { responseBody ->
                val jsonNode = objectMapper.readTree(responseBody)
                jsonNode.get("token").asText() // Extract the token from JSON response
            }

        // Step 2: Use the extracted token to access the protected endpoint
        mockMvc.get("/api/login/protected") {
            header("Authorization", "Bearer $token")
        }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should not access protected endpoint without JWT`() {
        mockMvc.get("/api/login/protected")
        .andExpect {
                status { isForbidden() }
            }
    }

    @Test
    fun `should not access protected endpoint with expired JWT`() {
        val email = "alice.smith@example.com"
        val token = JwtUtils.generateExpiredToken(email)

        val result = mockMvc.get("/api/login/protected") {
            header("Authorization", "Bearer $token")
            }
            .andExpect {
                status { isForbidden() }
            }
            .andReturn() // Retrieve the result object

        // Access the error message from MockHttpServletResponse
        val errorMessage = result.response.errorMessage

        // Assert that the error message is as expected
        assert(errorMessage == "Token has expired") {
            "Expected error message to be 'Token has expired', but was $errorMessage"
        }
    }
}