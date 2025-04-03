package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.UserAuthProvider
import nat20.kamppisserver.domain.enums.Provider
import org.springframework.data.jpa.repository.JpaRepository

interface UserAuthProviderRepository : JpaRepository<UserAuthProvider, Long> {
    fun findByProviderAndProviderUserId(provider: Provider, providerUserId: Long): UserAuthProvider?
}