package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.RoommatePreference
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.UserStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RoommatePreferenceRepository: JpaRepository<RoommatePreference, Long> {
    @Query("SELECT rp FROM RoommatePreference rp WHERE rp.user.id = :id AND rp.user.status = :status")
    fun findByUserIdAndStatus(@Param("id") id: Long, @Param("status") status: UserStatus): RoommatePreference?
}