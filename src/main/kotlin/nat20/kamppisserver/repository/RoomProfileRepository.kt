package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.RoomProfile
import org.springframework.data.jpa.repository.JpaRepository

interface RoomProfileRepository: JpaRepository<RoomProfile, Long> {

}