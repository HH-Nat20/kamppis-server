package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.FlatProfile
import nat20.kamppisserver.domain.Profile
import nat20.kamppisserver.domain.UserProfile
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository: JpaRepository<Profile, Long> {
    fun findByUserId(userId: Long): UserProfile?

    fun findByFlatId(flatId: Long): FlatProfile?
}