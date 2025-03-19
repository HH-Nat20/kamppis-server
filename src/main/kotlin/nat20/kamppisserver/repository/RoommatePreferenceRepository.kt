package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.RoommatePreference
import org.springframework.data.jpa.repository.JpaRepository

interface RoommatePreferenceRepository: JpaRepository<RoommatePreference, Long> {
}