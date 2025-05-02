package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.UserRequest
import nat20.kamppisserver.service.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController(
    private val loginService: LoginService,
) {

    @PostMapping("/mock")
    fun login(@RequestParam email: String): ResponseEntity<Map<String, String>> {
        return loginService.login(email)
    }

    @GetMapping("/protected")
    fun getProtectedData(@RequestAttribute("email") email: String): ResponseEntity<String> {
        // The request attribute is set in the jwtAuthenticationFilter during token authentication
        return ResponseEntity.ok("Hello, $email! This is protected data.")
    }

    @PostMapping("/github")
    suspend fun loginWithGitHub(@RequestParam code: String): ResponseEntity<Map<String, String>> {
        return loginService.loginWithGitHub(code)
    }

    @PostMapping("/signup")
    suspend fun signup(@RequestParam code: String, @Valid @RequestBody request: UserRequest): ResponseEntity<Map<String, String>> {
        return loginService.signup(code, request)
    }
}