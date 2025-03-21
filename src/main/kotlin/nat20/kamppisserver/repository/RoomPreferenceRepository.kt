package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.RoomPreference
import org.springframework.data.jpa.repository.JpaRepository

interface RoomPreferenceRepository: JpaRepository<RoomPreference, Long> {
}