package nat20.kamppisserver.api

import nat20.kamppisserver.ServerStartupInfo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A REST controller responsible for providing health status of the application.
 *
 * The `HealthController` exposes endpoints to verify the application's
 * availability and retrieve information about its startup time and
 * active Spring profiles.
 *
 * @constructor Initializes controller with a reference to `ServerStartupInfo`
 * which provides details about the server's startup.
 */
@RestController
@RequestMapping("/api/health")
class HealthController(
    private val serverStartupInfo: ServerStartupInfo
) {

    /**
     * Provides the health status of the server.
     *
     * @return a ResponseEntity containing a map with the server's health details:
     * - "status": indicates the server's current status, e.g., "ok".
     * - "upSince": the timestamp when the server started.
     * - "activeProfiles": a comma-separated string of active Spring profiles.
     */
    @GetMapping
    fun serverHealth(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok().body(mapOf(
            "status" to "ok",
            "upSince" to serverStartupInfo.upSince.toString(),
            "activeProfiles" to serverStartupInfo.activeProfiles.joinToString(",")
            ))
    }
}