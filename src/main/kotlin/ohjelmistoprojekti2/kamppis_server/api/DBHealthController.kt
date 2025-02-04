package ohjelmistoprojekti2.kamppis_server.api

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/db-health")
class DBHealthController(private val jdbcTemplate: JdbcTemplate) {

    @GetMapping
    fun dbHealth(): Map<String, String> {
        return try {
            jdbcTemplate.execute("SELECT 1")
            mapOf("status" to "ok")
        } catch (e: Exception) {
            mapOf("status" to "failed")
        }
    }
}
