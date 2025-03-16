package nat20.kamppisserver.api

import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController(
    private val userService: UserService
) {

    @PostMapping
    fun login(@RequestParam email: String): ResponseEntity<Map<String, String>> {
        if (email.endsWith("@example.com")) {
            try {
                userService.findActiveUserByEmail(email)
                val token = JwtUtils.generateJwtToken(email)
                return mapOf("token" to token).let { ResponseEntity.ok(it) }
            } catch (e: Error) {
                return ResponseEntity.badRequest().build()
            }
        } else {
            return ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/protected")
    fun getProtectedData(@RequestAttribute("email") email: String): String {
        return "Hello, $email! This is protected data."
    }

}