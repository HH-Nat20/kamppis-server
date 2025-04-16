package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.security.SecurityConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import kotlin.test.Test

/**
 * Tests GET calls (successful and unsuccessful) to api/db-health.
 */
@WebMvcTest(DBHealthController::class)
@Import(SecurityConfig::class)
class DBHealthControllerTest @Autowired constructor(
    val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should return ok when DB is reachable`() {
        every { jdbcTemplate.execute("SELECT 1") } returns Unit

        mockMvc.get("/api/db-health")
            .andExpect {
                status { isOk() }
                jsonPath("$.status") { value("ok") }
            }
    }

    @Test
    fun `should return 500 when DB is unreachable`() {
        every { jdbcTemplate.execute("SELECT 1") } throws RuntimeException("DB down")

        mockMvc.get("/api/db-health")
            .andExpect {
                status { isInternalServerError() }
                jsonPath("$.status") { value("failed") }
            }
    }
}