package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Profile
import nat20.kamppisserver.domain.enums.ProfileStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository: JpaRepository<Profile, Long> {
    fun findByIdAndStatus(id: Long, status: ProfileStatus = ProfileStatus.ACTIVE): Profile?
}