package nat20.kamppisserver.api

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/login")
class LoginController {

    private val key = "b8c485dc1b1db98ab477d6028a258609d729a18bda824d11d44e50a63b935e50" // TODO: Replace with proper secret in env

    private val secretKey = Keys.hmacShaKeyFor(key.toByteArray())

    @PostMapping
    fun login(@RequestParam email: String): ResponseEntity<Map<String, String>> {
        if (email == "alice.smith@example.com") {
            val token = generateJwtToken(email)
            return mapOf("token" to token).let { ResponseEntity.ok(it) }
        } else {
            return ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/protected")
    fun getProtectedData(@RequestAttribute("email") email: String): String {
        return "Hello, $email! This is protected data."
    }

    private fun generateJwtToken(email: String): String {
        val token = Jwts.builder()
        .claims()
        .subject(email)
        .issuedAt(Date())
        .expiration(Date(System.currentTimeMillis() + 3600000))
        .and()
        .signWith(secretKey)
        .compact()
        println("Generated token: $token")
        return token
    }
}