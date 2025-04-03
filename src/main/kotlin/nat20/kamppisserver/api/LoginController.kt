package nat20.kamppisserver.api

import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.security.GitHubAuthService
import nat20.kamppisserver.security.GitHubUserResponse
import nat20.kamppisserver.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController(
    private val userService: UserService,
    private val gitHubAuthService: GitHubAuthService
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
    fun getProtectedData(@RequestAttribute("email") email: String): ResponseEntity<String> {
        // The request attribute is set in the jwtAuthenticationFilter during token authentication
        return ResponseEntity.ok("Hello, $email! This is protected data.")
    }

    @PostMapping("/github")
    suspend fun loginWithGitHub(@RequestParam code: String): ResponseEntity<Map<String, String>> {
        val accessToken = gitHubAuthService.exchangeCodeForToken(code)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "Invalid GitHub code"))

        val userInfo: GitHubUserResponse = gitHubAuthService.getGitHubUserInfo(accessToken)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "No GitHub user found"))

        val email = userInfo.email ?: "dummy-email@example.com"
        val jwt = JwtUtils.generateJwtToken(email)

        return ResponseEntity.ok(mapOf("token" to jwt))
    }

}