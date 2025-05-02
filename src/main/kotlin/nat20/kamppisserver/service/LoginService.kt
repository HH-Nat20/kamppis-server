package nat20.kamppisserver.service

import jakarta.transaction.Transactional
import jakarta.validation.Valid
import nat20.kamppisserver.domain.RoomPreference
import nat20.kamppisserver.domain.RoommatePreference
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserRequest
import nat20.kamppisserver.domain.enums.Provider
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.repository.RoomPreferenceRepository
import nat20.kamppisserver.repository.RoommatePreferenceRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.security.AuthService
import nat20.kamppisserver.security.GitHubAuthService
import nat20.kamppisserver.security.GitHubUserResponse
import nat20.kamppisserver.security.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val userService: UserService,
    private val gitHubAuthService: GitHubAuthService,
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val roomPreferenceRepository: RoomPreferenceRepository,
    private val roommatePreferenceRepository: RoommatePreferenceRepository,
    private val jwtUtils: JwtUtils
) {
    fun login(email: String): ResponseEntity<Map<String, String>> {
        try {
            userService.findActiveUserByEmail(email)
            if (email.endsWith("@example.com")) {
                val token = jwtUtils.generateJwtToken(email)
                return mapOf("token" to token).let { ResponseEntity.ok(it) }
            } else {
                return ResponseEntity.badRequest().body(mapOf("error" to "Invalid email"))
            }
        } catch (e: Error) {
            return ResponseEntity.badRequest().build()
        }
    }

    suspend fun loginWithGitHub(code: String): ResponseEntity<Map<String, String>> {
        val accessToken = gitHubAuthService.exchangeCodeForToken(code)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "Invalid GitHub code"))

        val userInfo: GitHubUserResponse = gitHubAuthService.getGitHubUserInfo(accessToken)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "No GitHub user found"))

        val user = authService.getExistingOAuthUser(Provider.GITHUB, userInfo.id)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "No user found linked to given GitHub credentials. Use a different OAuth provider, or sign up."))

        val jwt = jwtUtils.generateJwtToken(user.email)
        return ResponseEntity.ok(
            mapOf(
                "token" to jwt,
                "userId" to user.id.toString()
            )
        )
    }

    //TODO Add Kdoc to explain /signup
    @Transactional
    suspend fun signup(code: String, request: UserRequest): ResponseEntity<Map<String, String>> {
        val accessToken = gitHubAuthService.exchangeCodeForToken(code)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "Invalid GitHub code"))

        val userInfo: GitHubUserResponse = gitHubAuthService.getGitHubUserInfo(accessToken)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "No GitHub user found"))

        val userDTO = userService.add(request)

        val user = userRepository.findByEmail(userDTO.email)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "User creation failed"))

        try {
            authService.signUpWithOAuth(Provider.GITHUB, userInfo.id, user)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(mapOf("error" to e.message!!))
        }

        // Add blank user profile
        val profile = UserProfile(user = user)
        user.userProfile = profile // Important for cascade/bidirectional sync
        profileRepository.save(profile)

        // Add blank preferences
        val roomPreference = RoomPreference(user = user)
        val roommatePreference = RoommatePreference(user = user)
        user.roomPreference = roomPreference
        user.roommatePreference = roommatePreference
        roomPreferenceRepository.save(roomPreference)
        roommatePreferenceRepository.save(roommatePreference)

        val jwt = jwtUtils.generateJwtToken(user.email)
        return ResponseEntity.ok(
            mapOf(
                "token" to jwt,
                "userId" to user.id.toString()
            )
        )
    }
}