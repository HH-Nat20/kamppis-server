package nat20.kamppisserver.api

import io.jsonwebtoken.security.Keys
import nat20.kamppisserver.security.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController {

    private val key = "b8c485dc1b1db98ab477d6028a258609d729a18bda824d11d44e50a63b935e50" // TODO: Replace with proper secret in env

    private val secretKey = Keys.hmacShaKeyFor(key.toByteArray())

    @PostMapping
    fun login(@RequestParam email: String): ResponseEntity<Map<String, String>> {
        if (email == "alice.smith@example.com") {
            val token = JwtUtils.generateJwtToken(email)
            return mapOf("token" to token).let { ResponseEntity.ok(it) }
        } else {
            return ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/protected")
    fun getProtectedData(@RequestAttribute("email") email: String): String {
        return "Hello, $email! This is protected data."
    }

}