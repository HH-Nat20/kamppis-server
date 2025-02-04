package nat20.kamppisserver.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    fun serverHealth(): Map<String, String> {
        return mapOf("status" to "ok")
    }
}