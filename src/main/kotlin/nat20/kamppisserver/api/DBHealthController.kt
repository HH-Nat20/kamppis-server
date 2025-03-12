package nat20.kamppisserver.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/db-health")
class DBHealthController(private val jdbcTemplate: JdbcTemplate) {

    @GetMapping
    fun dbHealth(): ResponseEntity<Map<String, String>> {
        return try {
            jdbcTemplate.execute("SELECT 1")
            ResponseEntity.ok(mapOf("status" to "ok"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("status" to "failed"))
        }
    }
}
