package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.FlatProfile
import org.springframework.data.jpa.repository.JpaRepository

interface FlatProfileRepository : JpaRepository<FlatProfile, Long> {
}