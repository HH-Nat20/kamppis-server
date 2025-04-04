package nat20.kamppisserver.api

import jakarta.transaction.Transactional
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.domain.UserProfileRequest
import nat20.kamppisserver.domain.UserRequest
import nat20.kamppisserver.domain.enums.Provider
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.security.AuthService
import nat20.kamppisserver.security.GitHubAuthService
import nat20.kamppisserver.security.GitHubUserResponse
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.service.UserProfileService
import nat20.kamppisserver.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController(
    private val userService: UserService,
    private val gitHubAuthService: GitHubAuthService,
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    @PostMapping
    fun login(@RequestParam email: String): ResponseEntity<Map<String, String>> {
        try {
            userService.findActiveUserByEmail(email)
            val token = JwtUtils.generateJwtToken(email)
            return mapOf("token" to token).let { ResponseEntity.ok(it) }
        } catch (e: Error) {
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

        val user = authService.getExistingOAuthUser(Provider.GITHUB, userInfo.id)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "No user found linked to given GitHub credentials. Use a different OAuth provider, or sign up."))

        val jwt = JwtUtils.generateJwtToken(user.email)
        return ResponseEntity.ok(mapOf("token" to jwt))
    }


    @PostMapping("/signup")
    @Transactional
    suspend fun signup(@RequestParam code: String, @Valid @RequestBody request: UserRequest): ResponseEntity<Map<String, String>> {
        val accessToken = gitHubAuthService.exchangeCodeForToken(code)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "Invalid GitHub code"))

        val userInfo: GitHubUserResponse = gitHubAuthService.getGitHubUserInfo(accessToken)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "No GitHub user found"))

        val userDTO = userService.add(request)

        var user = userRepository.findByEmail(userDTO.email)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "User creation failed"))

        val userProfile = userProfileRepository.save(
            UserProfile(
                user = user
            )
        )
        user.userProfile = userProfile
        user = userRepository.save(user)

        try {
            authService.signUpWithOAuth(Provider.GITHUB, userInfo.id, user)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(mapOf("error" to e.message!!))
        }

        val jwt = JwtUtils.generateJwtToken(user.email)
        return ResponseEntity.ok(mapOf("token" to jwt))
    }

}