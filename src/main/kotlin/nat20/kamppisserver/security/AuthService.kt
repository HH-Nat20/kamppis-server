package nat20.kamppisserver.security

import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserAuthProvider
import nat20.kamppisserver.domain.enums.Provider
import nat20.kamppisserver.repository.UserAuthProviderRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userAuthProviderRepository: UserAuthProviderRepository
) {
    @Transactional
    fun signUpWithOAuth(provider: Provider, providerUserId: Long, user: User): UserAuthProvider {

        if (userAuthProviderRepository.findByProviderAndProviderUserId(provider, providerUserId) != null) {
            throw IllegalArgumentException("Provider for $providerUserId already exists")
        }
        if (userRepository.findByEmail(user.email) != null) {
            throw IllegalArgumentException("User with this email already exists")
        }

        // Link the new OAuth provider to the user
        val newAuthProvider = UserAuthProvider(user = user, provider = provider, providerUserId = providerUserId)

        return userAuthProviderRepository.save(newAuthProvider)
    }

    fun getExistingOAuthUser(provider: Provider, providerUserId: Long): User? {

        val existingAuthProvider = userAuthProviderRepository.findByProviderAndProviderUserId(provider, providerUserId)

        return existingAuthProvider?.user

    }
}