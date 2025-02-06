package nat20.kamppisserver.repository

import org.springframework.data.jpa.repository.JpaRepository
import nat20.kamppisserver.domain.UserProfile
import java.util.*

interface UserProfileRepository: JpaRepository<UserProfile, Long> {
    fun findByUserId(userId: Long): Optional<UserProfile>
}