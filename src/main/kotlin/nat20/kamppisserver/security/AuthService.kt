package nat20.kamppisserver.security

import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserAuthProvider
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.Provider
import nat20.kamppisserver.repository.UserAuthProviderRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userAuthProviderRepository: UserAuthProviderRepository
) {
    @Transactional
    fun loginWithOAuth(provider: Provider, providerUserId: String, email: String?): User {

        val existingAuthProvider = userAuthProviderRepository.findByProviderAndProviderUserId(provider, providerUserId)

        if (existingAuthProvider != null) {
            return existingAuthProvider.user
        }

        var user = email?.let { userRepository.findByEmail(it) }

        if (user == null && email != null) {
            // TODO: Direct to create a new User
            // Throw an error for now
/*            throw UsernameNotFoundException("User not found")*/

            // create dummy user to start with
            user = userRepository.save(User(
                firstName = "Firstname",
                lastName = "Lastname",
                dateOfBirth = LocalDate.of(1980, 1, 1),
                gender = Gender.NOT_IMPORTANT,
                email = email))

            // Link the new OAuth provider to the user
            val newAuthProvider = UserAuthProvider(user = user, provider = provider, providerUserId = providerUserId)
            userAuthProviderRepository.save(newAuthProvider)

        } else {
            // User not found and email not provided
            throw IllegalArgumentException("If user is not found an email must be provided")
        }
        return user
    }
}