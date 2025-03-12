package nat20.kamppisserver.api

import nat20.kamppisserver.ServerStartupInfo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthController(
    private val serverStartupInfo: ServerStartupInfo
) {

    @GetMapping
    fun serverHealth(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf("status" to "ok", "startupInfo" to serverStartupInfo.upSince.toString()))
    }
}